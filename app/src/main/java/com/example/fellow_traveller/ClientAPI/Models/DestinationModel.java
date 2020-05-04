package com.example.fellow_traveller.ClientAPI.Models;

public class DestinationModel {

    private String id;
    private String title;
    private Float latitude;
    private Float longitude;

    public DestinationModel(String id, String title, Float latitude, Float longitude) {
        this.id = id;
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
