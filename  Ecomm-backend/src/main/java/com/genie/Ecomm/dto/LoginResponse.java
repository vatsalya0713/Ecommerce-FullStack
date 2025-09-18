package com.genie.Ecomm.dto;

public class LoginResponse {
    // JWT token field jisse backend frontend ko token bhejega
    private String token;

    // Getter and Setter for the token
    public String getToken() {
        return token;
    }

    public void setToken(String jwtToken) {
        this.token = jwtToken;
    }
}