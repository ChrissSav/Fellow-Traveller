package com.example.fellow_traveller.API;


import com.example.fellow_traveller.Models.User;
import com.example.fellow_traveller.Models.UserAuth;
import com.example.fellow_traveller.PlaceAutocomplete.PlaceAPi;
import com.example.fellow_traveller.PlaceAutocomplete.Predictions;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitService {


    @POST("user")
    Call<UserAuth> registerUser(
            @Body JsonObject user
    );


    @POST("auth/login")
    Call<UserAuth> loginUser(
            @Body JsonObject user_obj
    );

    @POST("auth/logout")
    Call<Status_Handling> LogoutUser(
            @Query("refresh_token") String token
    );


    @GET(".")
    Call<Status_Handling> Test();


    //PlaceAPi
    @GET("json")
    Call<PlaceAPi> getPlaces(
            @Query("input") String input,
            @Query("key") String key);
}
