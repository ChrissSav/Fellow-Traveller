package com.example.fellow_traveller.ClientAPI.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PassengerModel implements Parcelable {


    @SerializedName("user")
    @Expose
    private UserBaseModel user;
    @SerializedName("bags")
    @Expose
    private int bags;
    @SerializedName("pet")
    @Expose
    private Boolean pet;

    public PassengerModel(UserBaseModel user, int bags, Boolean pet) {
        this.user = user;
        this.bags = bags;
        this.pet = pet;
    }

    protected PassengerModel(Parcel in) {
        user = in.readParcelable(UserBaseModel.class.getClassLoader());
        bags = in.readInt();
        byte tmpPet = in.readByte();
        pet = tmpPet == 0 ? null : tmpPet == 1;
    }

    public static final Creator<PassengerModel> CREATOR = new Creator<PassengerModel>() {
        @Override
        public PassengerModel createFromParcel(Parcel in) {
            return new PassengerModel(in);
        }

        @Override
        public PassengerModel[] newArray(int size) {
            return new PassengerModel[size];
        }
    };

    public UserBaseModel getUser() {
        return user;
    }

    public void setUser(UserBaseModel user) {
        this.user = user;
    }

    public int getBags() {
        return bags;
    }

    public void setBags(int bags) {
        this.bags = bags;
    }

    public Boolean getPet() {
        return pet;
    }

    public void setPet(Boolean pet) {
        this.pet = pet;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(user, flags);
        dest.writeInt(bags);
        dest.writeByte((byte) (pet == null ? 0 : pet ? 1 : 2));
    }
}
