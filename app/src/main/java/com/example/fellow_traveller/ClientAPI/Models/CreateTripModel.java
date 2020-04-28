package com.example.fellow_traveller.ClientAPI.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateTripModel {

    @SerializedName("dest_from")
    @Expose
    private String destFrom;
    @SerializedName("dest_to")
    @Expose
    private String destTo;
    @SerializedName("timestamp")
    @Expose
    private Long timestamp;
    @SerializedName("pet")
    @Expose
    private String pet;
    @SerializedName("max_seats")
    @Expose
    private Integer maxSeats;
    @SerializedName("max_bags")
    @Expose
    private Integer maxBags;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("price")
    @Expose
    private Float price;
    @SerializedName("car_id")
    @Expose
    private Integer carId;

    public CreateTripModel(String dest_from, String dest_to, String pet, int max_seats, int max_bags, int car_id, float price, Long timestamp, String msg) {
        this.destFrom = destFrom;
        this.destTo = destTo;
        this.timestamp = timestamp;
        this.pet = pet;
        this.maxSeats = maxSeats;
        this.maxBags = maxBags;
        this.msg = msg;
        this.price = price;
        this.carId = carId;
    }
}