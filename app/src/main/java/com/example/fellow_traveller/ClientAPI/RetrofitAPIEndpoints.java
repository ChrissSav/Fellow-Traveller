package com.example.fellow_traveller.ClientAPI;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import com.example.fellow_traveller.ClientAPI.Models.UserAuth;
import com.google.gson.JsonObject;

public interface RetrofitAPIEndpoints {
    @POST("/auth/login")
    Call<UserAuth> userAuthenticate(@Body JsonObject user);
}
