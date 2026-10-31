package com.logistics.platform.config;

import com.logistics.platform.entity.User;
import com.logistics.platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        name = "app.bootstrap-admin.enabled",
        havingValue = "true"
)
public class AdminBootstrapRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.bootstrap-admin.username:}")
    private String username;

    @Value("${app.bootstrap-admin.email:}")
    private String email;

    @Value("${app.bootstrap-admin.password:}")
    private String password;

    public AdminBootstrapRunner(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            throw new IllegalStateException(
                    "Admin bootstrap credentials must be configured."
            );
        }

        if (password.length() < 12) {
            throw new IllegalStateException(
                    "Bootstrap admin password must be at least 12 characters."
            );
        }

        boolean adminExists = userRepository.findAll()
                .stream()
                .anyMatch(user -> "ADMIN".equalsIgnoreCase(user.getRole()));

        if (adminExists) {
            System.out.println("ADMIN account already exists. Bootstrap skipped.");
            return;
        }

        if (userRepository.findByUsername(username) != null ||
                userRepository.findByEmail(email) != null) {
            throw new IllegalStateException(
                    "Bootstrap username or email is already registered."
            );
        }

        User admin = new User();
        admin.setUsername(username);
        admin.setEmail(email);
        admin.setPasswordHash(passwordEncoder.encode(password));
        admin.setRole("ADMIN");

        userRepository.save(admin);

        System.out.println("Initial ADMIN account created successfully.");
    }
}