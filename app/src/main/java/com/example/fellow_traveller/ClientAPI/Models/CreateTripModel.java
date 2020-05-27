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
    @SerializedName("pickup_point")
    @Expose
    private DestinationModel PickUP;
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

    public CreateTripModel(DestinationModel destFrom, DestinationModel destTo,DestinationModel PickUP, Long timestamp, Boolean hasPet, Integer maxSeats, Integer maxBags, String msg, Float price, Integer carId) {
        this.destFrom = destFrom;
        this.destTo = destTo;
        this.PickUP = PickUP;
        this.timestamp = timestamp;
        this.hasPet = hasPet;
        this.maxSeats = maxSeats;
        this.maxBags = maxBags;
        this.msg = msg;
        this.price = price;
        this.carId = carId;
    }

    public DestinationModel getDestFrom() {
        return destFrom;
    }

    public void setDestFrom(DestinationModel destFrom) {
        this.destFrom = destFrom;
    }

    public DestinationModel getDestTo() {
        return destTo;
    }

    public void setDestTo(DestinationModel destTo) {
        this.destTo = destTo;
    }

    public DestinationModel getPickUP() {
        return PickUP;
    }

    public void setPickUP(DestinationModel pickUP) {
        PickUP = pickUP;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getHasPet() {
        return hasPet;
    }

    public void setHasPet(Boolean hasPet) {
        this.hasPet = hasPet;
    }

    public Integer getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(Integer maxSeats) {
        this.maxSeats = maxSeats;
    }

    public Integer getMaxBags() {
        return maxBags;
    }

    public void setMaxBags(Integer maxBags) {
        this.maxBags = maxBags;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }
}