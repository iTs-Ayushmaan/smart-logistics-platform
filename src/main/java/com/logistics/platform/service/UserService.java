package com.logistics.platform.service;

import com.logistics.platform.dto.CreateUserRequest;
import com.logistics.platform.dto.UpdateUserRequest;
import com.logistics.platform.dto.UserResponseDTO;
import com.logistics.platform.entity.User;
import com.logistics.platform.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Get all users
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Get user by ID
    public Optional<UserResponseDTO> getUserById(Integer id) {
        return userRepository.findById(id)
                .map(this::toResponseDTO);
    }

    // Get user by email
    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return null;
        }

        return toResponseDTO(user);
    }

    // Get user by username
    public UserResponseDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return null;
        }

        return toResponseDTO(user);
    }

    // Create user
    public UserResponseDTO createUser(CreateUserRequest request) {

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());

        // Password hashing will be added with authentication/security.
        user.setPasswordHash(request.getPassword());

        User savedUser = userRepository.save(user);

        return toResponseDTO(savedUser);
    }

    // Update user
    public UserResponseDTO updateUser(Integer id, UpdateUserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update only fields that were provided
        if (request.getUsername() != null) {
            user.setUsername(request.getUsername());
        }

        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }

        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }

        if (request.getPassword() != null) {
            // Password hashing will be added with authentication/security.
            user.setPasswordHash(request.getPassword());
        }

        User updatedUser = userRepository.save(user);

        return toResponseDTO(updatedUser);
    }

    // Delete user
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    // Convert Entity -> Response DTO
    private UserResponseDTO toResponseDTO(User user) {

        UserResponseDTO dto = new UserResponseDTO();

        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());

        return dto;
    }
}