package com.example.fellow_traveller.Notification;

import com.example.fellow_traveller.ClientAPI.Models.UserBaseModel;

public class NotificationItem {
    private UserBaseModel user;
    private String Status;


    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public UserBaseModel getUser() {
        return user;
    }

    public void setUser(UserBaseModel user) {
        this.user = user;
    }
}
