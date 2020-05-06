package com.example.fellow_traveller.ClientAPI.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateTripModel {

    @SerializedName("destFrom")
    @Expose
    private DestinationModel destFrom;
    @SerializedName("destTo")
    @Expose
    private DestinationModel destTo;
    @SerializedName("timestamp")
    @Expose
    private Long timestamp;
    @SerializedName("pet")
    @Expose
    private Boolean hasPet;
    @SerializedName("max_seats")
    @Expose
    private Integer maxSeats;
    @SerializedName("max_bags")
    @Expose
    private Integer maxBags;
    @SerializedName("message")
    @Expose
    private String msg;
    @SerializedName("price")
    @Expose
    private Float price;
    @SerializedName("car_id")
    @Expose
    private Integer carId;

    public CreateTripModel(DestinationModel destFrom, DestinationModel destTo, Long timestamp, Boolean hasPet, Integer maxSeats, Integer maxBags, String msg, Float price, Integer carId) {
        this.destFrom = destFrom;
        this.destTo = destTo;
        this.timestamp = timestamp;
        this.hasPet = hasPet;
        this.maxSeats = maxSeats;
        this.maxBags = maxBags;
        this.msg = msg;
        this.price = price;
        this.carId = carId;
    }
}