package com.example.fellow_traveller.ClientAPI.Callbacks;

import com.example.fellow_traveller.ClientAPI.Models.StatusHandleModel;


public interface TripRegisterCallBack {
    void onSuccess(StatusHandleModel status);
    void onFailure(String errorMsg);
}
