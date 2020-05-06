package com.example.fellow_traveller.ClientAPI.Models;

import com.google.gson.annotations.SerializedName;

public class DestinationModel {

    @SerializedName("place_id")
    private String placeId;
    private String title;
    private Float latitude;
    private Float longitude;

    public DestinationModel(String placeId, String title, Float latitude, Float longitude) {
        this.placeId = placeId;
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
