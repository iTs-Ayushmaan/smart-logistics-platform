package com.logistics.platform.dto;

public class AuthenticationResponse {

    private String message;
    private Integer userId;
    private String username;
    private String role;
    private String token;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(
            String message,
            Integer userId,
            String username,
            String role,
            String token
    ) {
        this.message = message;
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}