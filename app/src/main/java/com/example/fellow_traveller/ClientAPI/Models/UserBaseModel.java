package com.example.fellow_traveller.ClientAPI.Models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserBaseModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("rate")
    @Expose
    private double rate;
    @SerializedName("reviews")
    @Expose
    private int reviews;
    @SerializedName("picture")
    @Expose
    private String picture;

    public UserBaseModel(String name, String surname) {
        this.firstName = name;
        this.lastName = surname;

    }

    public double getRate() {
        return rate;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
}

    public String getFullName() {
        return firstName + " " + lastName;
    }



}