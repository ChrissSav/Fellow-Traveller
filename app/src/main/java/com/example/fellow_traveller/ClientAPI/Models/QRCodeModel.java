package com.example.fellow_traveller.ClientAPI.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class QRCodeModel implements Parcelable {

    private String code;

    protected QRCodeModel(Parcel in) {
        code = in.readString();
    }

    public static final Creator<QRCodeModel> CREATOR = new Creator<QRCodeModel>() {
        @Override
        public QRCodeModel createFromParcel(Parcel in) {
            return new QRCodeModel(in);
        }

        @Override
        public QRCodeModel[] newArray(int size) {
            return new QRCodeModel[size];
        }
    };

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
    }
}
