package com.example.fellow_traveller.ClientAPI.Callbacks;

import com.example.fellow_traveller.ClientAPI.Models.TripModel;

import java.util.ArrayList;

public interface SearchTripsCallback {
    void onSuccess(ArrayList<TripModel> trips);

    void onFailure(String errorMsg);
}

