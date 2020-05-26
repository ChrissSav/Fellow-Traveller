package com.example.fellow_traveller.ClientAPI.Callbacks;

import com.example.fellow_traveller.ClientAPI.Models.BooleanResponseModel;

public interface BooleanResponseModelCallBack {
    void onSuccess(BooleanResponseModel notificationModels);
    void onFailure(String msg);
}
