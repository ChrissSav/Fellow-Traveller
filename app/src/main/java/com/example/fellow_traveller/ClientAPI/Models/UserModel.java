package com.example.fellow_traveller.ClientAPI.Models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("surname")
    @Expose
    private String surname;
    @SerializedName("id")
    @Expose
    private Integer id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}