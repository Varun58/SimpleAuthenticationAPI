package com.example.abillion.response;

public class LoginApiResponse {

    private String token;

    public LoginApiResponse() {}

    public LoginApiResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
