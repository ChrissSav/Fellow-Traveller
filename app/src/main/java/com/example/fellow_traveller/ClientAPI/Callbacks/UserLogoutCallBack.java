package com.example.fellow_traveller.ClientAPI.Callbacks;

import com.example.fellow_traveller.ClientAPI.Models.StatusHandleModel;
import com.example.fellow_traveller.ClientAPI.Models.UserAuthModel;

public interface UserLogoutCallBack {
    void onSuccess(StatusHandleModel user);
    void onFailure(String errorMsg);
}
