package com.example.fellow_traveller.ClientAPI.Models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserUpdateModel {

    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("about_me")
    @Expose
    private String aboutMe;
    @SerializedName("phone")
    @Expose
    private String phone;


    public UserUpdateModel(String firstName, String lastName, String aboutMe, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.aboutMe = aboutMe;
        this.phone = phone;
    }

}