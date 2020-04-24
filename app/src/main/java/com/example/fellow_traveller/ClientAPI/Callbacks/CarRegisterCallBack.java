package com.example.fellow_traveller.ClientAPI.Callbacks;

import com.example.fellow_traveller.ClientAPI.Models.CarModel;

public interface CarRegisterCallBack {
    void onSuccess(CarModel car);
    void onFailure(String errorMsg);
}
