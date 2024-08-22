package com.debasis.eventManagement.controller;

import com.debasis.eventManagement.exception.UserException;
import com.debasis.eventManagement.model.AuthProvider;
import com.debasis.eventManagement.model.User;
import com.debasis.eventManagement.request.LoginRequest;
import com.debasis.eventManagement.request.UserDTO;
import com.debasis.eventManagement.response.LoginResponse;
import com.debasis.eventManagement.response.LoginUserDto;
import com.debasis.eventManagement.response.RegisterUserDto;
import com.debasis.eventManagement.response.Response;
import com.debasis.eventManagement.service.UserService;
import com.debasis.eventManagement.serviceImpl.AuthenticationService;
import com.debasis.eventManagement.serviceImpl.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        String jwtToken = jwtService.generateToken(registeredUser);
        LoginResponse loginResponse = LoginResponse.builder().token(jwtToken).expiresIn(jwtService.getExpirationTime()).build();
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto , HttpServletResponse response) {
       User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = LoginResponse.builder().token(jwtToken).expiresIn(jwtService.getExpirationTime()).build();

        return ResponseEntity.ok(loginResponse);
    }

}
