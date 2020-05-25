package com.example.fellow_traveller.ClientAPI.Callbacks;

import com.example.fellow_traveller.ClientAPI.Models.UserInfoModel;


public interface UserInfoModelCallBack {
    void onSuccess(UserInfoModel userInfoModel);

    void onFailure(String errorMsg);
}
