package com.example.fellow_traveller.ClientAPI.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserUpdateModel {

    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("about_me")
    @Expose
    private String aboutMe;
    @SerializedName("phone")
    @Expose
    private String phone;

    public UserUpdateModel(String firstName, String lastName, String picture, String aboutMe, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.picture = picture;
        this.aboutMe = aboutMe;
        this.phone = phone;
    }

}