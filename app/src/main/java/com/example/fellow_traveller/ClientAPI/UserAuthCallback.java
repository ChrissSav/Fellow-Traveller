package com.example.fellow_traveller.ClientAPI;

import com.example.fellow_traveller.ClientAPI.Models.UserAuth;
// TODO Create callbacks package inside the ClientAPI
public interface UserAuthCallback {
    void onSuccess(UserAuth user);
    void onFailure();
}
