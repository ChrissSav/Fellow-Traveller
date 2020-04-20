package com.example.fellow_traveller.PlaceAutocomplete;

import java.util.ArrayList;

public class PlaceAPi {
    private ArrayList<Predictions> predictions;
    private String status;

    public ArrayList<Predictions> getPredictions() {
        return predictions;
    }

    public void setPredictions(ArrayList<Predictions> predictions) {
        this.predictions = predictions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


