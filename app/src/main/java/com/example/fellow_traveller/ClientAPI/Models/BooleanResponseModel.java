package com.example.fellow_traveller.ClientAPI.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class BooleanResponseModel implements Parcelable {

    private Boolean flag;

    protected BooleanResponseModel(Parcel in) {
        byte tmpFlag = in.readByte();
        flag = tmpFlag == 0 ? null : tmpFlag == 1;
    }

    public static final Creator<BooleanResponseModel> CREATOR = new Creator<BooleanResponseModel>() {
        @Override
        public BooleanResponseModel createFromParcel(Parcel in) {
            return new BooleanResponseModel(in);
        }

        @Override
        public BooleanResponseModel[] newArray(int size) {
            return new BooleanResponseModel[size];
        }
    };

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (flag == null ? 0 : flag ? 1 : 2));
    }
}
