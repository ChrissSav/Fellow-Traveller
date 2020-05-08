package com.example.fellow_traveller.ClientAPI.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class FilterModel implements Parcelable {

    private Integer timestampMin;
    private Integer timestampMax;
    private Integer seatsMin;
    private Integer seatsMax;
    private Integer bagsMin;
    private Integer bagsMax;
    private Integer priceMin;
    private Integer priceMax;
    private Boolean havePet;
    private Integer range;

    public FilterModel(Integer timestampMin, Integer timestampMax, Integer seatsMin, Integer seatsMax, Integer bagsMin, Integer bagsMax, Integer priceMin, Integer priceMax, Boolean havePet, Integer range) {
        this.timestampMin = timestampMin;
        this.timestampMax = timestampMax;
        this.seatsMin = seatsMin;
        this.seatsMax = seatsMax;
        this.bagsMin = bagsMin;
        this.bagsMax = bagsMax;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.havePet = havePet;
        this.range = range;
    }

    public FilterModel() {
        this.timestampMin = null;
        this.timestampMax = null;
        this.seatsMin = null;
        this.seatsMax = null;
        this.bagsMin = null;
        this.bagsMax = null;
        this.priceMin = null;
        this.priceMax = null;
        this.havePet = null;
        this.range = null;
    }

    protected FilterModel(Parcel in) {
        if (in.readByte() == 0) {
            timestampMin = null;
        } else {
            timestampMin = in.readInt();
        }
        if (in.readByte() == 0) {
            timestampMax = null;
        } else {
            timestampMax = in.readInt();
        }
        if (in.readByte() == 0) {
            seatsMin = null;
        } else {
            seatsMin = in.readInt();
        }
        if (in.readByte() == 0) {
            seatsMax = null;
        } else {
            seatsMax = in.readInt();
        }
        if (in.readByte() == 0) {
            bagsMin = null;
        } else {
            bagsMin = in.readInt();
        }
        if (in.readByte() == 0) {
            bagsMax = null;
        } else {
            bagsMax = in.readInt();
        }
        if (in.readByte() == 0) {
            priceMin = null;
        } else {
            priceMin = in.readInt();
        }
        if (in.readByte() == 0) {
            priceMax = null;
        } else {
            priceMax = in.readInt();
        }
        byte tmpHavePet = in.readByte();
        havePet = tmpHavePet == 0 ? null : tmpHavePet == 1;
        if (in.readByte() == 0) {
            range = null;
        } else {
            range = in.readInt();
        }
    }

    public static final Creator<FilterModel> CREATOR = new Creator<FilterModel>() {
        @Override
        public FilterModel createFromParcel(Parcel in) {
            return new FilterModel(in);
        }

        @Override
        public FilterModel[] newArray(int size) {
            return new FilterModel[size];
        }
    };

    public Integer getTimestampMin() {
        return timestampMin;
    }

    public void setTimestampMin(Integer timestampMin) {
        this.timestampMin = timestampMin;
    }

    public Integer getTimestampMax() {
        return timestampMax;
    }

    public void setTimestampMax(Integer timestampMax) {
        this.timestampMax = timestampMax;
    }

    public Integer getSeatsMin() {
        return seatsMin;
    }

    public void setSeatsMin(Integer seatsMin) {
        this.seatsMin = seatsMin;
    }

    public Integer getSeatsMax() {
        return seatsMax;
    }

    public void setSeatsMax(Integer seatsMax) {
        this.seatsMax = seatsMax;
    }

    public Integer getBagsMin() {
        return bagsMin;
    }

    public void setBagsMin(Integer bagsMin) {
        this.bagsMin = bagsMin;
    }

    public Integer getBagsMax() {
        return bagsMax;
    }

    public void setBagsMax(Integer bagsMax) {
        this.bagsMax = bagsMax;
    }

    public Integer getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(Integer priceMin) {
        this.priceMin = priceMin;
    }

    public Integer getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(Integer priceMax) {
        this.priceMax = priceMax;
    }

    public Boolean getHavePet() {
        return havePet;
    }

    public void setHavePet(Boolean havePet) {
        this.havePet = havePet;
    }

    public Integer getRange() {
        return range;
    }

    public void setRange(Integer range) {
        this.range = range;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (timestampMin == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(timestampMin);
        }
        if (timestampMax == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(timestampMax);
        }
        if (seatsMin == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(seatsMin);
        }
        if (seatsMax == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(seatsMax);
        }
        if (bagsMin == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(bagsMin);
        }
        if (bagsMax == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(bagsMax);
        }
        if (priceMin == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(priceMin);
        }
        if (priceMax == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(priceMax);
        }
        parcel.writeByte((byte) (havePet == null ? 0 : havePet ? 1 : 2));
        if (range == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(range);
        }
    }
}
