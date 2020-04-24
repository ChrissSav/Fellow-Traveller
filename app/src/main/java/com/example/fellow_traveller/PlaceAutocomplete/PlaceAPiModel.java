package com.example.fellow_traveller.PlaceAutocomplete;

import java.util.ArrayList;

public class PlaceAPiModel {
    private ArrayList<PredictionsModel> predictions;
    private String status;

    public ArrayList<PredictionsModel> getPredictions() {
        return predictions;
    }

    public void setPredictions(ArrayList<PredictionsModel> predictions) {
        this.predictions = predictions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


