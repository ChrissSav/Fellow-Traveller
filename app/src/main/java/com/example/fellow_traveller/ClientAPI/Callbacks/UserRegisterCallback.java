package com.example.fellow_traveller.ClientAPI.Callbacks;

import com.example.fellow_traveller.API.Status_Handling;
import com.example.fellow_traveller.ClientAPI.Models.UserAuthModel;
import com.example.fellow_traveller.Models.User;

public interface UserRegisterCallback {
        void onSuccess(UserAuthModel status);
        void onFailure(String errorMsg);
}
