package com.example.fellow_traveller.ClientAPI.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CreateReviewModel implements Parcelable {

    private String description;
    private int rate;
    private long timestamp;
    @SerializedName("target_id")
    private int targetId;
    @SerializedName("trip_id")
    private int tripId;


    public CreateReviewModel(String description, int rate, long timestamp, int targetId, int tripId) {
        this.tripId = tripId;
        this.description = description;
        this.rate = rate;
        this.timestamp = timestamp;
        this.targetId = targetId;
    }


    protected CreateReviewModel(Parcel in) {
        description = in.readString();
        rate = in.readInt();
        timestamp = in.readLong();
        targetId = in.readInt();
        tripId = in.readInt();
    }

    public static final Creator<CreateReviewModel> CREATOR = new Creator<CreateReviewModel>() {
        @Override
        public CreateReviewModel createFromParcel(Parcel in) {
            return new CreateReviewModel(in);
        }

        @Override
        public CreateReviewModel[] newArray(int size) {
            return new CreateReviewModel[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeInt(rate);
        dest.writeLong(timestamp);
        dest.writeInt(targetId);
        dest.writeInt(tripId);
    }
}
