package com.example.fellow_traveller.PlacesAPI.Models;

import com.google.gson.annotations.SerializedName;

public class ResultModel {
    @SerializedName("formatted_address")
    private String formattedAddress;
    private GeometryModel geometry;
    @SerializedName("place_id")
    private String placeΙd;


    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public GeometryModel getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryModel geometry) {
        this.geometry = geometry;
    }

    public String getPlaceΙd() {
        return placeΙd;
    }

    public void setPlaceΙd(String placeΙd) {
        this.placeΙd = placeΙd;
    }
}
