package com.logistics.platform.dto;

import lombok.Getter;
import lombok.Setter;

//@Setter
//@Getter
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}