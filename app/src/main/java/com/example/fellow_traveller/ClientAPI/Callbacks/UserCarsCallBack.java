package com.example.fellow_traveller.ClientAPI.Callbacks;

import com.example.fellow_traveller.ClientAPI.Models.CarModel;

import java.util.ArrayList;

public interface UserCarsCallBack {
    void onSuccess(ArrayList<CarModel> user);
    void onFailure(String errorMsg);
}
