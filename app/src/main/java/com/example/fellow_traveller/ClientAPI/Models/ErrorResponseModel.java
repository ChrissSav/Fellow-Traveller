package com.example.fellow_traveller.ClientAPI.Models;

import com.google.gson.annotations.SerializedName;

public class ErrorResponseModel {
    private String status;
    private DetailModel detail;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DetailModel getDetail() {
        return detail;
    }

    public void setDetail(DetailModel detail) {
        this.detail = detail;
    }
}
