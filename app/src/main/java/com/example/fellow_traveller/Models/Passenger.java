package com.example.fellow_traveller.Models;

public class Passenger {

    private UserBase user;
    private int bags;
    private String pets;


    public UserBase getUser() {
        return user;
    }

    public void setUser(UserBase user) {
        this.user = user;
    }

    public int getBags() {
        return bags;
    }

    public void setBags(int bags) {
        this.bags = bags;
    }

    public String getPets() {
        return pets;
    }

    public void setPets(String pets) {
        this.pets = pets;
    }
}
