package com.logistics.platform.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {

    @Size(max = 255, message = "Username must not exceed 255 characters")
    private String username;

    @Email(message = "Invalid email format")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    private String email;

    @Size(
            min = 6,
            max = 255,
            message = "Password must be between 6 and 255 characters"
    )
    private String password;

    @Size(max = 50, message = "Role must not exceed 50 characters")
    private String role;
}