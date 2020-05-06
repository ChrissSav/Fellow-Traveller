package com.example.fellow_traveller.ClientAPI.Models;

public class SearchDestinationsModel {

    private LatLongModel destFrom;
    private LatLongModel destTo;

    public SearchDestinationsModel(LatLongModel destFrom, LatLongModel destTo) {
        this.destFrom = destFrom;
        this.destTo = destTo;
    }

    public LatLongModel getDestFrom() {
        return destFrom;
    }

    public void setDestFrom(LatLongModel destFrom) {
        this.destFrom = destFrom;
    }

    public LatLongModel getDestTo() {
        return destTo;
    }

    public void setDestTo(LatLongModel destTo) {
        this.destTo = destTo;
    }
}
