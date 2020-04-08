package com.example.fellow_traveller.Models;

import android.app.Application;

public class GlobalClass extends Application {

    private UserAuth current_user;

    public UserAuth getCurrent_user() {
        return current_user;
    }

    public void setCurrent_user(UserAuth current_user) {
        this.current_user = current_user;
    }
}
