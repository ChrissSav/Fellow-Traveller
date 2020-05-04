package com.example.fellow_traveller.PlacesAPI.Models;

import com.google.gson.annotations.SerializedName;

public class LocationModel {
    @SerializedName("lat")
    private Float latitude ;
    @SerializedName("lng")
    private Float longitude;


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
}
