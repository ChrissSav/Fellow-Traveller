package com.example.fellow_traveller.ClientAPI.Callbacks;


import com.example.fellow_traveller.ClientAPI.Models.ReviewModel;

import java.util.ArrayList;

public interface ReviewModelCallBack {

    void onSuccess(ArrayList<ReviewModel> reviews);
    void onFailure(String errorMsg);
}
