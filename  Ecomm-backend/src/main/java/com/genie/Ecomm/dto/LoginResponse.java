package com.genie.Ecomm.dto;

public class LoginResponse {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String jwtToken) {
        this.token = jwtToken;
    }
}