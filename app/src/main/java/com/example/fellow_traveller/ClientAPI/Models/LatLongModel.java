package com.example.fellow_traveller.ClientAPI.Models;

import androidx.annotation.NonNull;

public class LatLongModel {

    private Float latitude;
    private Float longitude;

    public LatLongModel(Float latitude, Float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    @NonNull
    @Override
    public String toString() {
        return "latitude: "+latitude+" longitude: "+longitude;
    }
}
