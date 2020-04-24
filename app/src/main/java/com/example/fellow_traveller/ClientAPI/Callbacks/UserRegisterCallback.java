package com.example.fellow_traveller.ClientAPI.Callbacks;

import com.example.fellow_traveller.ClientAPI.Models.UserAuthModel;

public interface UserRegisterCallback {
        void onSuccess(UserAuthModel user);
        void onFailure(String errorMsg);
}
