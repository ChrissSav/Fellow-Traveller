package com.example.fellow_traveller.ClientAPI.Models;

import com.google.gson.annotations.SerializedName;

public class DetailModel {
    @SerializedName("status_code")
    private int statusCode;
    private String msg;


    public DetailModel(int statusCode) {
        this.statusCode = statusCode;
        this.msg = "msg";
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
