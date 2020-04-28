package com.example.fellow_traveller.PlaceAPI.CallBack;

import com.example.fellow_traveller.PlaceAPI.Models.PlaceAPiModel;

public interface PlaceApiCallBack {
    void onSuccess(PlaceAPiModel placeAPiModel);
    void onFailure(String errorMsg);
}
