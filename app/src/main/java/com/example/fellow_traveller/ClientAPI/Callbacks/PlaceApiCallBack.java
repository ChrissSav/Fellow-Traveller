package com.example.fellow_traveller.ClientAPI.Callbacks;

import com.example.fellow_traveller.PlaceAutocomplete.PlaceAPiModel;

public interface PlaceApiCallBack {

    void onSuccess(PlaceAPiModel placeAPiModel);
    void onFailure(String errorMsg);
}
