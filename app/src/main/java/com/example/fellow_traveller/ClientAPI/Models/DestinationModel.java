package com.example.fellow_traveller.ClientAPI.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DestinationModel  implements Parcelable {




    @SerializedName("place_id")
    private String placeId;
    private String title;
    private Float latitude;
    private Float longitude;

    public DestinationModel(String placeId, String title, Float latitude, Float longitude) {
        this.placeId = placeId;
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public DestinationModel(Parcel in) {
        placeId = in.readString();
        title = in.readString();
        latitude = in.readFloat();
        longitude = in.readFloat();
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(placeId);
        parcel.writeString(title);
        parcel.writeFloat(latitude);
        parcel.writeFloat(longitude);
    }
    public static final Creator<DestinationModel> CREATOR = new Creator<DestinationModel>() {
        @Override
        public DestinationModel createFromParcel(Parcel in) {
            return new DestinationModel(in);
        }

        @Override
        public DestinationModel[] newArray(int size) {
            return new DestinationModel[size];
        }
    };
}
