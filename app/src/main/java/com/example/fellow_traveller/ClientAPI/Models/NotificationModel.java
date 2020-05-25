package com.example.fellow_traveller.ClientAPI.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificationModel implements Parcelable {

    private int id;
    private UserBaseModel user;
    private TripModel trip;
    @SerializedName("has_read")
    private Boolean hasRead;
    private Long timestamp;
    @SerializedName("type_of")
    private String typeOf;


    protected NotificationModel(Parcel in) {
        id = in.readInt();
        user = in.readParcelable(UserBaseModel.class.getClassLoader());
        trip = in.readParcelable(TripModel.class.getClassLoader());
        byte tmpHasRead = in.readByte();
        hasRead = tmpHasRead == 0 ? null : tmpHasRead == 1;
        if (in.readByte() == 0) {
            timestamp = null;
        } else {
            timestamp = in.readLong();
        }
        typeOf = in.readString();
    }

    public static final Creator<NotificationModel> CREATOR = new Creator<NotificationModel>() {
        @Override
        public NotificationModel createFromParcel(Parcel in) {
            return new NotificationModel(in);
        }

        @Override
        public NotificationModel[] newArray(int size) {
            return new NotificationModel[size];
        }
    };

    public String getTypeOf() {
        return typeOf;
    }

    public void setTypeOf(String typeOf) {
        this.typeOf = typeOf;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserBaseModel getUser() {
        return user;
    }

    public void setUser(UserBaseModel user) {
        this.user = user;
    }

    public TripModel getTrip() {
        return trip;
    }

    public void setTrip(TripModel trip) {
        this.trip = trip;
    }

    public Boolean getHasRead() {
        return hasRead;
    }

    public void setHasRead(Boolean hasRead) {
        this.hasRead = hasRead;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(user, flags);
        dest.writeParcelable(trip, flags);
        dest.writeByte((byte) (hasRead == null ? 0 : hasRead ? 1 : 2));
        if (timestamp == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(timestamp);
        }
        dest.writeString(typeOf);
    }
}
