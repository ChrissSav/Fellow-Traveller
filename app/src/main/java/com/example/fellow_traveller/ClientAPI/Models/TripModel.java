package com.example.fellow_traveller.ClientAPI.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TripModel implements Parcelable {

    @SerializedName("dest_from")
    @Expose
    private String destFrom;
    @SerializedName("dest_to")
    @Expose
    private String destTo;
    @SerializedName("timestamp")
    @Expose
    private Integer timestamp;
    @SerializedName("pet")
    @Expose
    private boolean pet;
    @SerializedName("max_seats")
    @Expose
    private Integer maxSeats;
    @SerializedName("max_bags")
    @Expose
    private Integer maxBags;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("creator")
    @Expose
    private UserBaseModel creatorUser;
    @SerializedName("car")
    @Expose
    private CarModel car;
    @SerializedName("current_seats")
    @Expose
    private Integer currentSeats;
    @SerializedName("current_bags")
    @Expose
    private Integer currentBags;
    @SerializedName("passengers")
    @Expose
    private ArrayList<PassengerModel> passengers = null;


    protected TripModel(Parcel in) {
        destFrom = in.readString();
        destTo = in.readString();
        if (in.readByte() == 0) {
            timestamp = null;
        } else {
            timestamp = in.readInt();
        }
        pet = in.readByte() != 0;
        if (in.readByte() == 0) {
            maxSeats = null;
        } else {
            maxSeats = in.readInt();
        }
        if (in.readByte() == 0) {
            maxBags = null;
        } else {
            maxBags = in.readInt();
        }
        msg = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readInt();
        }
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        creatorUser = in.readParcelable(UserBaseModel.class.getClassLoader());
        car = in.readParcelable(CarModel.class.getClassLoader());
        if (in.readByte() == 0) {
            currentSeats = null;
        } else {
            currentSeats = in.readInt();
        }
        if (in.readByte() == 0) {
            currentBags = null;
        } else {
            currentBags = in.readInt();
        }
    }

    public static final Creator<TripModel> CREATOR = new Creator<TripModel>() {
        @Override
        public TripModel createFromParcel(Parcel in) {
            return new TripModel(in);
        }

        @Override
        public TripModel[] newArray(int size) {
            return new TripModel[size];
        }
    };

    public boolean isPet() {
        return pet;
    }

    public UserBaseModel getCreatorUser() {
        return creatorUser;
    }

    public void setCreatorUser(UserBaseModel creatord) {
        this.creatorUser = creatord;
    }

    public String getDestFrom() {
        return destFrom;
    }

    public void setDestFrom(String destFrom) {
        this.destFrom = destFrom;
    }

    public String getDestTo() {
        return destTo;
    }

    public void setDestTo(String destTo) {
        this.destTo = destTo;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public boolean getPet() {
        return pet;
    }

    public String getPetString() {
        return pet? "Επιτρέπω": "Δεν επιτρέπω";
    }
    public void setPet(boolean pet) {
        this.pet = pet;
    }

    public Integer getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(Integer maxSeats) {
        this.maxSeats = maxSeats;
    }

    public Integer getMaxBags() {
        return maxBags;
    }

    public void setMaxBags(Integer maxBags) {
        this.maxBags = maxBags;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public CarModel getCar() {
        return car;
    }

    public void setCar(CarModel car) {
        this.car = car;
    }

    public Integer getCurrentSeats() {
        return currentSeats;
    }

    public void setCurrentSeats(Integer currentSeats) {
        this.currentSeats = currentSeats;
    }

    public Integer getCurrentBags() {
        return currentBags;
    }

    public void setCurrentBags(Integer currentBags) {
        this.currentBags = currentBags;
    }

    public ArrayList<PassengerModel> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<PassengerModel> passengers) {
        this.passengers = passengers;
    }

    public String getSeatStatus() {
        return currentSeats + "/" + maxSeats;
    }

    public String getBagsStatus() {
        return currentBags + "/" + maxBags;
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
        dest.writeString(destFrom);
        dest.writeString(destTo);
        if (timestamp == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(timestamp);
        }
        dest.writeByte((byte) (pet ? 1 : 0));
        if (maxSeats == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(maxSeats);
        }
        if (maxBags == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(maxBags);
        }
        dest.writeString(msg);
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(price);
        }
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeParcelable(creatorUser, flags);
        dest.writeParcelable(car, flags);
        if (currentSeats == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(currentSeats);
        }
        if (currentBags == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(currentBags);
        }
    }
}
