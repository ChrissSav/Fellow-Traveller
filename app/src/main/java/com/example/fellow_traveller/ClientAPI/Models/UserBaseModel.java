package com.example.fellow_traveller.ClientAPI.Models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class UserBaseModel implements Parcelable {

    @SerializedName("id")
    private Integer id;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("rate")
    private double rate;
    @SerializedName("reviews")
    private int reviews;
    @SerializedName("picture")
    private String picture;

    public UserBaseModel(String name, String surname) {
        this.firstName = name;
        this.lastName = surname;

    }


    protected UserBaseModel(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        firstName = in.readString();
        lastName = in.readString();
        rate = in.readDouble();
        reviews = in.readInt();
        picture = in.readString();
    }

    public static final Creator<UserBaseModel> CREATOR = new Creator<UserBaseModel>() {
        @Override
        public UserBaseModel createFromParcel(Parcel in) {
            return new UserBaseModel(in);
        }

        @Override
        public UserBaseModel[] newArray(int size) {
            return new UserBaseModel[size];
        }
    };

    public double getRate() {
        return rate;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
}

    public String getFullName() {
        return firstName + " " + lastName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeDouble(rate);
        dest.writeInt(reviews);
        dest.writeString(picture);
    }
}