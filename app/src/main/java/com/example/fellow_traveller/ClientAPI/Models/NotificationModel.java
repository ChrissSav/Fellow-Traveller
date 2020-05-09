package com.example.fellow_traveller.ClientAPI.Models;

public class NotificationModel {

    private int id;
    private UserBaseModel user;
    private TripModel trip;
    private String read;


    public NotificationModel(int id, UserBaseModel user) {
        this.id = id;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserBaseModel getUser() {
        return user;
    }

    public void setUser(UserBaseModel user) {
        this.user = user;
    }

    public TripModel getTrip() {
        return trip;
    }

    public void setTrip(TripModel trip) {
        this.trip = trip;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }
}
