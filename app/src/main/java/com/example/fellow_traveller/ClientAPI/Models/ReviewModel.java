package com.example.fellow_traveller.ClientAPI.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReviewModel implements Parcelable {

    private String description;
    private Float rate;
    private Long timestamp;
    private UserBaseModel user;


    protected ReviewModel(Parcel in) {
        description = in.readString();
        if (in.readByte() == 0) {
            rate = null;
        } else {
            rate = in.readFloat();
        }
        if (in.readByte() == 0) {
            timestamp = null;
        } else {
            timestamp = in.readLong();
        }
        user = in.readParcelable(UserBaseModel.class.getClassLoader());
    }

    public static final Creator<ReviewModel> CREATOR = new Creator<ReviewModel>() {
        @Override
        public ReviewModel createFromParcel(Parcel in) {
            return new ReviewModel(in);
        }

        @Override
        public ReviewModel[] newArray(int size) {
            return new ReviewModel[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public UserBaseModel getUser() {
        return user;
    }

    public void setUser(UserBaseModel user) {
        this.user = user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        if (rate == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(rate);
        }
        if (timestamp == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(timestamp);
        }
        dest.writeParcelable(user, flags);
    }

    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM yyyy");
        Date date = new Date((long) timestamp * 1000);
        return dateFormat.format(date);
    }

    public String getTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date((long) timestamp * 1000);
        return dateFormat.format(date);
    }
}
