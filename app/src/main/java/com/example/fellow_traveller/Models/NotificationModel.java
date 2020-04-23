package com.example.fellow_traveller.Models;

public class NotificationModel {

    private int id;
    private UserBase user;
    private Trip trip;
    private String read;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserBase getUser() {
        return user;
    }

    public void setUser(UserBase user) {
        this.user = user;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }
}
