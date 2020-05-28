package com.example.fellow_traveller.ClientAPI.Callbacks;

public interface QRCodeModelCallBack {
    void onSuccess(String qrCode);
    void onFailure(String msg);

}
