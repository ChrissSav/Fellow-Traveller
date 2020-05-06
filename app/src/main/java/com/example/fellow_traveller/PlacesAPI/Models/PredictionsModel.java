package com.example.fellow_traveller.PlacesAPI.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class PredictionsModel implements Parcelable {
    private String description;
    private String id;
    @SerializedName("place_id")
    private String placeId;

    public PredictionsModel() {
        this.description = "";
        this.id = "";
        this.placeId = "";
    }

    protected PredictionsModel(Parcel in) {
        description = in.readString();
        id = in.readString();
        placeId = in.readString();
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

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeString(id);
        dest.writeString(placeId);
    }

    @NonNull
    @Override
    public String toString() {
        return id+ " | "+ placeId+" | "+ description;
    }
}




















