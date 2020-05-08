package com.example.fellow_traveller.ClientAPI.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TripModel implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("destFrom")
    @Expose
    private DestinationModel destFrom;
    @SerializedName("destTo")
    @Expose
    private DestinationModel destTo;

    @SerializedName("creator")
    @Expose
    private UserBaseModel creatorUser;

    @SerializedName("car")
    @Expose
    private CarModel car;

    @SerializedName("timestamp")
    @Expose
    private Long timestamp;


    @SerializedName("current_seats")
    @Expose
    private Integer currentSeats;
    @SerializedName("max_seats")
    @Expose
    private Integer maxSeats;
    @SerializedName("current_bags")
    @Expose
    private Integer currentBags;
    @SerializedName("max_bags")
    @Expose
    private Integer maxBags;


    @SerializedName("pet")
    @Expose
    private Boolean pet;

    @SerializedName("price")
    @Expose
    private Float price;

    @SerializedName("message")
    @Expose
    private String message;




    @SerializedName("passengers")
    @Expose
    private ArrayList<PassengerModel> passengers;

    @SerializedName("active")
    @Expose
    private Boolean active;


    protected TripModel(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        destFrom = in.readParcelable(DestinationModel.class.getClassLoader());
        destTo = in.readParcelable(DestinationModel.class.getClassLoader());
        creatorUser = in.readParcelable(UserBaseModel.class.getClassLoader());
        car = in.readParcelable(CarModel.class.getClassLoader());
        if (in.readByte() == 0) {
            timestamp = null;
        } else {
            timestamp = in.readLong();
        }
        if (in.readByte() == 0) {
            currentSeats = null;
        } else {
            currentSeats = in.readInt();
        }
        if (in.readByte() == 0) {
            maxSeats = null;
        } else {
            maxSeats = in.readInt();
        }
        if (in.readByte() == 0) {
            currentBags = null;
        } else {
            currentBags = in.readInt();
        }
        if (in.readByte() == 0) {
            maxBags = null;
        } else {
            maxBags = in.readInt();
        }
        byte tmpPet = in.readByte();
        pet = tmpPet == 0 ? null : tmpPet == 1;
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readFloat();
        }
        message = in.readString();
        passengers = in.createTypedArrayList(PassengerModel.CREATOR);
        byte tmpActive = in.readByte();
        active = tmpActive == 0 ? null : tmpActive == 1;
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

    //====================================================================
    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DestinationModel getDestFrom() {
        return destFrom;
    }

    public void setDestFrom(DestinationModel destFrom) {
        this.destFrom = destFrom;
    }

    public DestinationModel getDestTo() {
        return destTo;
    }

    public void setDestTo(DestinationModel destTo) {
        this.destTo = destTo;
    }

    public UserBaseModel getCreatorUser() {
        return creatorUser;
    }

    public void setCreatorUser(UserBaseModel creatorUser) {
        this.creatorUser = creatorUser;
    }

    public CarModel getCar() {
        return car;
    }

    public void setCar(CarModel car) {
        this.car = car;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getCurrentSeats() {
        return currentSeats;
    }

    public void setCurrentSeats(Integer currentSeats) {
        this.currentSeats = currentSeats;
    }

    public Integer getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(Integer maxSeats) {
        this.maxSeats = maxSeats;
    }

    public Integer getCurrentBags() {
        return currentBags;
    }

    public void setCurrentBags(Integer currentBags) {
        this.currentBags = currentBags;
    }

    public Integer getMaxBags() {
        return maxBags;
    }

    public void setMaxBags(Integer maxBags) {
        this.maxBags = maxBags;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    public String getPetString() {
        return pet? "Επιτρέπω": "Δεν επιτρέπω";
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
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeParcelable(destFrom, flags);
        dest.writeParcelable(destTo, flags);
        dest.writeParcelable(creatorUser, flags);
        dest.writeParcelable(car, flags);
        if (timestamp == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(timestamp);
        }
        if (currentSeats == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(currentSeats);
        }
        if (maxSeats == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(maxSeats);
        }
        if (currentBags == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(currentBags);
        }
        if (maxBags == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(maxBags);
        }
        dest.writeByte((byte) (pet == null ? 0 : pet ? 1 : 2));
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(price);
        }
        dest.writeString(message);
        dest.writeTypedList(passengers);
        dest.writeByte((byte) (active == null ? 0 : active ? 1 : 2));
    }

}
