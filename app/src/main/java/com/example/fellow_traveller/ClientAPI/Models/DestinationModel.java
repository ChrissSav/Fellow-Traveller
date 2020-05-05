package com.example.fellow_traveller.ClientAPI.Models;

public class DestinationModel {

    private String place_id;
    private String title;
    private Float latitude;
    private Float longitude;

    public DestinationModel(String place_id, String title, Float latitude, Float longitude) {
        this.place_id = place_id;
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
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
