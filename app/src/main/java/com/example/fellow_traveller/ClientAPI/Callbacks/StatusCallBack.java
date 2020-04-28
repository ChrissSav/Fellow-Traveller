package com.example.fellow_traveller.ClientAPI.Callbacks;

public interface StatusCallBack {
    void onSuccess(String status);

    void onFailure(String errorMsg);
}
