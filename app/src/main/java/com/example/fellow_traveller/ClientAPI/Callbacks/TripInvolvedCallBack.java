package com.example.fellow_traveller.ClientAPI.Callbacks;

import com.example.fellow_traveller.ClientAPI.Models.TripInvolvedModel;

import java.util.ArrayList;

public interface TripInvolvedCallBack {
    void onSuccess(ArrayList<TripInvolvedModel> trips);

    void onFailure(String errorMsg);
}
