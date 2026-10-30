package com.logistics.platform.service;

import com.logistics.platform.dto.AuthenticationRequest;
import com.logistics.platform.dto.AuthenticationResponse;
import com.logistics.platform.entity.User;
import com.logistics.platform.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.logistics.platform.security.JwtService;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthenticationResponse authenticate(
            AuthenticationRequest request
    ) {
        String login = request.getUsernameOrEmail();

        User user = userRepository.findByUsername(login);

        if (user == null) {
            user = userRepository.findByEmail(login);
        }

        if (user == null ||
                !passwordEncoder.matches(
                        request.getPassword(),
                        user.getPasswordHash()
                )) {
            throw new RuntimeException("Invalid username/email or password");
        }

        String token = jwtService.generateToken(
                user.getUsername(),
                user.getRole()
        );

        return new AuthenticationResponse(
                "Login successful",
                user.getId(),
                user.getUsername(),
                user.getRole(),
                token
        );
    }
}