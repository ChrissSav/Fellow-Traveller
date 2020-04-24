package com.example.fellow_traveller.ClientAPI.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TripModel {

    @SerializedName("dest_from")
    @Expose
    private String destFrom;
    @SerializedName("dest_to")
    @Expose
    private String destTo;
    @SerializedName("timestamp")
    @Expose
    private Integer timestamp;
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
    private Integer price;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("creator")
    @Expose
    private UserModel creator;
    @SerializedName("car")
    @Expose
    private CarModel car;
    @SerializedName("current_seats")
    @Expose
    private Integer currentSeats;
    @SerializedName("current_bags")
    @Expose
    private Integer currentBags;
    @SerializedName("passengers")
    @Expose
    private ArrayList<PassengerModel> passengers = null;


    public String getDestFrom() {
        return destFrom;
    }

    public void setDestFrom(String destFrom) {
        this.destFrom = destFrom;
    }

    public String getDestTo() {
        return destTo;
    }

    public void setDestTo(String destTo) {
        this.destTo = destTo;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public String getPet() {
        return pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserModel getCreator() {
        return creator;
    }

    public void setCreator(UserModel creator) {
        this.creator = creator;
    }

    public CarModel getCar() {
        return car;
    }

    public void setCar(CarModel car) {
        this.car = car;
    }

    public Integer getCurrentSeats() {
        return currentSeats;
    }

    public void setCurrentSeats(Integer currentSeats) {
        this.currentSeats = currentSeats;
    }

    public Integer getCurrentBags() {
        return currentBags;
    }

    public void setCurrentBags(Integer currentBags) {
        this.currentBags = currentBags;
    }

    public ArrayList<PassengerModel> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<PassengerModel> passengers) {
        this.passengers = passengers;
    }
}
