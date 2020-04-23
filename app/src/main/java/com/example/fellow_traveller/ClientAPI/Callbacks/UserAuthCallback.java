package com.example.fellow_traveller.ClientAPI.Callbacks;

import com.example.fellow_traveller.ClientAPI.Models.UserAuthModel;
// TODO Create callbacks package inside the ClientAPI
public interface UserAuthCallback {
    void onSuccess(UserAuthModel user);
    void onFailure(String errorMsg);
}
