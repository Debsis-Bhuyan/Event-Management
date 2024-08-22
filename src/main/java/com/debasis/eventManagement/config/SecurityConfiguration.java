package com.debasis.eventManagement.config;

import com.debasis.eventManagement.model.User;
import com.debasis.eventManagement.repository.UserRepository;
import com.debasis.eventManagement.serviceImpl.AuthenticationService;
import com.debasis.eventManagement.utils.Utils;
import com.nimbusds.oauth2.sdk.id.Audience;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.oidc.authentication.OidcIdTokenDecoderFactory;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfiguration(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            AuthenticationProvider authenticationProvider,
            UserRepository userRepository, PasswordEncoder passwordEncoder
    ) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> {
                            auth.requestMatchers(HttpMethod.OPTIONS, "*/**").permitAll();
                            auth
                                    .requestMatchers("/api/auth/**", "/api/event/get-all", "/api/event/get-event/**", "*/login/oauth2/code/**", "/login-success")
                                    .permitAll();
                            auth.anyRequest().authenticated();
                        }
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth -> {
                    oauth.clientRegistrationRepository(clientRegistrationRepository());
                    oauth.successHandler((request, response, authentication) -> {
                        if (authentication instanceof OAuth2AuthenticationToken token) {
                            User authenticatedUser = oauth2authenticate(token);
                            response.sendRedirect("http://localhost:5173/oauth/login?status=success&userName=" + authenticatedUser.getEmail());
                        } else {
                            response.sendRedirect("http://localhost:5173/oauth/login?status=error");
                        }
                    });
                    oauth.failureHandler((request, response, exception) -> System.out.println("Request is failed: " + exception.getMessage()));
                });
        ;

        return http.build();
    }

    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:8888"));
        configuration.setAllowedMethods(List.of("GET", "POST", "DELETE", "PUT"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }


    public User oauth2authenticate(OAuth2AuthenticationToken token) {
        OAuth2User oAuth2User = token.getPrincipal();
        String userEmail = oAuth2User.getAttribute("email");
        Optional<User> existingUser = userRepository.findByEmail(userEmail);
        if (existingUser.isEmpty()) {
            User user = new User();
            user.setEmail(userEmail);
            user.setAuthProvider(token.getAuthorizedClientRegistrationId());
            user.setFullName(oAuth2User.getAttribute("name"));
            user.setPassword(passwordEncoder.encode(Utils.DEFAULT_PASSWORD));
            userRepository.save(user);
            System.out.println("User registration successful: " + user);
            return user;
        }
        return existingUser.get();
    }


    @Value("${oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @Value("${oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;

    @Value("${spring.security.oauth2.client.registration.facebook.client-id}")
    private String facebookClientId;

    @Value("${spring.security.oauth2.client.registration.facebook.client-secret}")
    private String facebookClientSecret;

    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(Arrays.asList(
                getGoogleClientRegistration(),
                getFacebookClientRegistration()
        ));
    }

    private ClientRegistration getGoogleClientRegistration() {
        return ClientRegistration.withRegistrationId("google")
                .clientId(googleClientId)
                .clientSecret(googleClientSecret)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
                .scope("openid", "profile", "email")
                .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
                .tokenUri("https://oauth2.googleapis.com/token")
                .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                .userInfoUri("https://openidconnect.googleapis.com/v1/userinfo")
                .userNameAttributeName(IdTokenClaimNames.SUB)
                .issuerUri("https://accounts.google.com")
                .clientName("google")
                .build();
    }

    private ClientRegistration getFacebookClientRegistration() {
        return ClientRegistration.withRegistrationId("facebook")
                .clientId(facebookClientId)
                .clientSecret(facebookClientSecret)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
                .scope("email")
                .authorizationUri("https://www.facebook.com/v12.0/dialog/oauth")
                .tokenUri("https://graph.facebook.com/v12.0/oauth/access_token")
                .userInfoUri("https://graph.facebook.com/me?fields=id,name,email")
                .userNameAttributeName("id")
                .clientName("facebook")
                .build();
    }

    @Bean
    public JwtDecoderFactory<ClientRegistration> idTokenDecoderFactory() {
        OidcIdTokenDecoderFactory idTokenDecoderFactory = new OidcIdTokenDecoderFactory();
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer("https://accounts.google.com");
        idTokenDecoderFactory.setJwtValidatorFactory(clientRegistration -> withIssuer);
        return idTokenDecoderFactory;
    }

}