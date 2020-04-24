package com.example.fellow_traveller.ClientAPI.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Car  implements Parcelable {
    private int id;
    private String brand;
    private String model;
    private String plate;
    private String color;

    public Car(String brand, String model, String plate) {
        this.brand = brand;
        this.model = model;
        this.plate = plate;
    }

    public Car(int id,String brand, String model, String plate, String color) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.plate = plate;
        this.color = color;

    }

    protected Car(Parcel in) {
        id = in.readInt();
        brand = in.readString();
        model = in.readString();
        plate = in.readString();
        color = in.readString();
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }


    public String getDescription() {
       return brand + "  " + model + " | " + plate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(brand);
        dest.writeString(model);
        dest.writeString(plate);
        dest.writeString(color);
    }
}
