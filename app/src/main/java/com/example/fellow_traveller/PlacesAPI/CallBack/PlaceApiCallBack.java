package com.example.fellow_traveller.PlacesAPI.CallBack;

import com.example.fellow_traveller.PlacesAPI.Models.PlaceAPiModel;

public interface PlaceApiCallBack {
    void onSuccess(PlaceAPiModel placeAPiModel);
    void onFailure(String errorMsg);
}
