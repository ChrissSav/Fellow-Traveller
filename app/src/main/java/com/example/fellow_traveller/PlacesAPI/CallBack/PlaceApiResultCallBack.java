package com.example.fellow_traveller.PlacesAPI.CallBack;

import com.example.fellow_traveller.PlacesAPI.Models.ResultModel;

public interface PlaceApiResultCallBack {
    void onSuccess(ResultModel resultModel);
    void onFailure(String errorMsg);
}
