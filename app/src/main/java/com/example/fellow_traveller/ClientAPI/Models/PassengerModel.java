package com.example.fellow_traveller.ClientAPI.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PassengerModel {

    @SerializedName("user")
    @Expose
    private UserModel user;
    @SerializedName("bags")
    @Expose
    private int bags;
    @SerializedName("pet")
    @Expose
    private String pet;


    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public int getBags() {
        return bags;
    }

    public void setBags(int bags) {
        this.bags = bags;
    }

    public String getPet() {
        return pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }
}
