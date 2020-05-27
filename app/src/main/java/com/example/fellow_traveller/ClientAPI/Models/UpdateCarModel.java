package com.example.fellow_traveller.ClientAPI.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class UpdateCarModel implements Parcelable {
    private int id;
    private String brand;
    private String model;
    private String color;


    public UpdateCarModel(int id, String brand, String model, String color) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.color = color;
    }

    protected UpdateCarModel(Parcel in) {
        id = in.readInt();
        brand = in.readString();
        model = in.readString();
        color = in.readString();
    }

    public static final Creator<UpdateCarModel> CREATOR = new Creator<UpdateCarModel>() {
        @Override
        public UpdateCarModel createFromParcel(Parcel in) {
            return new UpdateCarModel(in);
        }

        @Override
        public UpdateCarModel[] newArray(int size) {
            return new UpdateCarModel[size];
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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
        dest.writeString(color);
    }
}
