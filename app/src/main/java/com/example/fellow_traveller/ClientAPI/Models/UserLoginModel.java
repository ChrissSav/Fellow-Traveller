package com.example.fellow_traveller.ClientAPI.Models;

public class UserLoginModel {
    private String email;
    private String password;

    public UserLoginModel(String email, String password) {
        this.email = email;
        this.password = password;
    }
}