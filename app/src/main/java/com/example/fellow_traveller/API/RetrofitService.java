package com.example.fellow_traveller.API;


import com.example.fellow_traveller.ClientAPI.Models.NotificationModel;
import com.example.fellow_traveller.ClientAPI.Status_Handling;
import com.example.fellow_traveller.PlaceAutocomplete.PlaceAPi;
import com.google.gson.JsonObject;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitService {




    @POST("auth/logout")
    Call<Status_Handling> LogoutUser(
            @Body JsonObject token
    );



    //Trip
    @POST("/trips")
    Call<Status_Handling> registerTrip(
            @Body JsonObject trip
    );





    @GET(".")
    Call<Status_Handling> Test();

    @GET("/notifications")
    Call<List<NotificationModel>> getNotifications();


    //PlaceAPi

    @GET("json")
    Call<PlaceAPi> getPlaces(
            @Query("input") String input,
            @Query("key") String key,
            @Query("language") String language,
            @Query("components") String components);
}
