package com.example.fellow_traveller.Models;

import java.util.ArrayList;

public class Trip {
    private int id;
    private String dest_from;
    private String dest_to;
    private String date;
    private String time;
    private String pet;
    private int max_seats;
    private int current_seats;
    private int max_bags;
    private int current_bags;
    private UserBase creator;
    private Car car;
    private ArrayList<Passenger> passengers;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDest_from() {
        return dest_from;
    }

    public void setDest_from(String dest_from) {
        this.dest_from = dest_from;
    }

    public String getDest_to() {
        return dest_to;
    }

    public void setDest_to(String dest_to) {
        this.dest_to = dest_to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPet() {
        return pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }

    public int getMax_seats() {
        return max_seats;
    }

    public void setMax_seats(int max_seats) {
        this.max_seats = max_seats;
    }

    public int getCurrent_seats() {
        return current_seats;
    }

    public void setCurrent_seats(int current_seats) {
        this.current_seats = current_seats;
    }

    public int getMax_bags() {
        return max_bags;
    }

    public void setMax_bags(int max_bags) {
        this.max_bags = max_bags;
    }

    public int getCurrent_bags() {
        return current_bags;
    }

    public void setCurrent_bags(int current_bags) {
        this.current_bags = current_bags;
    }

    public UserBase getCreator() {
        return creator;
    }

    public void setCreator(UserBase creator) {
        this.creator = creator;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
    }


    public String getSeatsStatus() {
        return current_seats+"/"+max_seats;
    }

    public String getBagsStatus() {
        return current_bags+"/"+max_bags;
    }
}
