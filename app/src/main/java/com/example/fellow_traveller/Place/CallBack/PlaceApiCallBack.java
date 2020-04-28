package com.example.fellow_traveller.Place.CallBack;

import com.example.fellow_traveller.Place.Models.PlaceAPiModel;

public interface PlaceApiCallBack {
    void onSuccess(PlaceAPiModel placeAPiModel);
    void onFailure(String errorMsg);
}
