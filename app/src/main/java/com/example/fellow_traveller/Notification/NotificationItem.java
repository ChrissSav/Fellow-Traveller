package com.example.fellow_traveller.Notification;

import com.example.fellow_traveller.Models.User;

public class NotificationItem {
    private User user;
    private String Status;


    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
