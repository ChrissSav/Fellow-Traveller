package com.example.fellow_traveller.ClientAPI.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddCarModel {

    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("plate")
    @Expose
    private String plate;
    @SerializedName("color")
    @Expose
    public String color;

    public AddCarModel(String brand, String model, String plate, String color) {
        this.brand = brand;
        this.model = model;
        this.plate = plate;
        this.color = color;
    }

}