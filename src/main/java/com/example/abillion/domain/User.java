package com.example.abillion.domain;

import javax.validation.constraints.NotNull;

public class User {

    @NotNull
    private String username;

    @NotNull
    private String password;

    public User() { }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
