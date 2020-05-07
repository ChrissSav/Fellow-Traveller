package com.example.fellow_traveller.ClientAPI.Models;

import com.google.gson.annotations.SerializedName;

public class CreatePassengerModel {

    @SerializedName("trip_id")
    private int tripId;
    private int bags;
    private Boolean pet;

    public CreatePassengerModel(int tripId, int bags, Boolean pet) {
        this.tripId = tripId;
        this.bags = bags;
        this.pet = pet;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
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
