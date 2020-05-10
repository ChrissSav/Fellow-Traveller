package com.example.fellow_traveller.ClientAPI.Callbacks;

import com.example.fellow_traveller.ClientAPI.Models.NotificationModel;

import java.util.ArrayList;

public interface NotificationCallBack {
    void onSuccess(ArrayList<NotificationModel> notificationModels);
    void onFailure(String msg);
}
