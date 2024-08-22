package com.debasis.eventManagement.serviceImpl;

import com.debasis.eventManagement.model.User;
import com.debasis.eventManagement.repository.UserRepository;
import com.debasis.eventManagement.response.LoginUserDto;
import com.debasis.eventManagement.response.RegisterUserDto;
import com.debasis.eventManagement.utils.Utils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDto input) {
        User user = User.builder().fullName(input.getFullName()).email(input.getEmail()).password(passwordEncoder.encode(input.getPassword())).authProvider("LOCAL").build();

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));

        return userRepository.findByEmail(input.getEmail()).orElseThrow();
    }

}
