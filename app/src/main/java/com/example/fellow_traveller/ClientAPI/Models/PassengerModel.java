package com.example.fellow_traveller.ClientAPI.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PassengerModel {


    @SerializedName("user")
    @Expose
    private UserBaseModel user;
    @SerializedName("bags")
    @Expose
    private int bags;
    @SerializedName("pet")
    @Expose
    private Boolean pet;

    public PassengerModel(UserBaseModel user, int bags, Boolean pet) {
        this.user = user;
        this.bags = bags;
        this.pet = pet;
    }

    public UserBaseModel getUser() {
        return user;
    }

    public void setUser(UserBaseModel user) {
        this.user = user;
    }

    public int getBags() {
        return bags;
    }

    public void setBags(int bags) {
        this.bags = bags;
    }

    public Boolean getPet() {
        return pet;
    }

    public void setPet(Boolean pet) {
        this.pet = pet;
    }
}
