package com.example.fellow_traveller.PlacesAPI.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class PredictionsModel implements Parcelable {
    private String description;
    private String id;
    private String place_id;

    public PredictionsModel() {
        this.description = "";
        this.id = "";
        this.place_id = "";
    }

    protected PredictionsModel(Parcel in) {
        description = in.readString();
        id = in.readString();
        place_id = in.readString();
    }

    public static final Creator<PredictionsModel> CREATOR = new Creator<PredictionsModel>() {
        @Override
        public PredictionsModel createFromParcel(Parcel in) {
            return new PredictionsModel(in);
        }

        @Override
        public PredictionsModel[] newArray(int size) {
            return new PredictionsModel[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeString(id);
        dest.writeString(place_id);
    }

    @NonNull
    @Override
    public String toString() {
        return id+ " | "+ place_id+" | "+ description;
    }
}




















