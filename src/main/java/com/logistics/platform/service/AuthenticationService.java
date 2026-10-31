package com.logistics.platform.service;

import com.logistics.platform.dto.AuthenticationRequest;
import com.logistics.platform.dto.AuthenticationResponse;
import com.logistics.platform.dto.RegisterRequest;
import com.logistics.platform.dto.UserResponseDTO;
import com.logistics.platform.entity.User;
import com.logistics.platform.repository.UserRepository;
import com.logistics.platform.security.JwtService;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    // Customer registration
    public UserResponseDTO register(RegisterRequest request) {

        String username = request.getUsername().trim();
        String email = request.getEmail().trim().toLowerCase();

        if (userRepository.findByUsername(username) != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Username already exists"
            );
        }

        if (userRepository.findByEmail(email) != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already registered"
            );
        }

        User user = new User();

        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(
                passwordEncoder.encode(request.getPassword())
        );

        // Never accept the role from the registration request.
        user.setRole("CUSTOMER");

        User savedUser = userRepository.save(user);

        UserResponseDTO response = new UserResponseDTO();
        response.setId(savedUser.getId());
        response.setUsername(savedUser.getUsername());
        response.setEmail(savedUser.getEmail());
        response.setRole(savedUser.getRole());

        return response;
    }

    // Login
    public AuthenticationResponse authenticate(
            AuthenticationRequest request
    ) {

        String login = request.getUsernameOrEmail().trim();

        User user = userRepository.findByUsername(login);

        if (user == null) {
            user = userRepository.findByEmail(login.toLowerCase());
        }

        if (user == null ||
                user.getPasswordHash() == null ||
                !passwordEncoder.matches(
                        request.getPassword(),
                        user.getPasswordHash()
                )) {

            throw new BadCredentialsException(
                    "Invalid username/email or password"
            );
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