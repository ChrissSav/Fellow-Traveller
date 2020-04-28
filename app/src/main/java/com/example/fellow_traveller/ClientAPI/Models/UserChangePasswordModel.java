package com.example.fellow_traveller.ClientAPI.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserChangePasswordModel {

    @SerializedName("password_prev")
    @Expose
    private String oldPassword;
    @SerializedName("password_new")
    @Expose
    private String newPassword;

    public UserChangePasswordModel(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

}