package com.example.fellow_traveller.ClientAPI.Callbacks;

import com.example.fellow_traveller.ClientAPI.Models.StatusHandleModel;

public interface StatusCallBack {
    void onSuccess(String status);
    void onFailure(String errorMsg);
}
