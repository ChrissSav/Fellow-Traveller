package com.example.fellow_traveller.ClientAPI.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class QRCodeVerifyModel implements Parcelable {

    @SerializedName("trip_id")
    private int tripId;
    @SerializedName("code")
    private  String qrCode;

    public QRCodeVerifyModel(int tripId, String qrCode) {
        this.tripId = tripId;
        this.qrCode = qrCode;
    }

    protected QRCodeVerifyModel(Parcel in) {
        tripId = in.readInt();
        qrCode = in.readString();
    }

    public static final Creator<QRCodeVerifyModel> CREATOR = new Creator<QRCodeVerifyModel>() {
        @Override
        public QRCodeVerifyModel createFromParcel(Parcel in) {
            return new QRCodeVerifyModel(in);
        }

        @Override
        public QRCodeVerifyModel[] newArray(int size) {
            return new QRCodeVerifyModel[size];
        }
    };

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tripId);
        dest.writeString(qrCode);
    }
}
