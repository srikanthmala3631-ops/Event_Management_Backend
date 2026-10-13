package com.eventapp.dto;

/**
 * DTO = "Data Transfer Object". It simply describes the shape of the
 * JSON body the frontend sends us. This one is used for POST /api/auth/login
 */
public class LoginRequest {

    private String email;
    private String password;

    public LoginRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
