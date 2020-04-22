package com.example.fellow_traveller.API;


import com.example.fellow_traveller.Models.Car;
import com.example.fellow_traveller.Models.User;
import com.example.fellow_traveller.Models.UserAuth;
import com.example.fellow_traveller.PlaceAutocomplete.PlaceAPi;
import com.example.fellow_traveller.PlaceAutocomplete.Predictions;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitService {


    @POST("auth/login")
    Call<UserAuth> loginUser(
            @Body JsonObject user_obj
    );

    @POST("auth/logout")
    Call<Status_Handling> LogoutUser(
            @Query("refresh_token") String token
    );

    //User
    @POST("user")
    Call<UserAuth> registerUser(
            @Body JsonObject user
    );





    //Trip
    @POST("/trips")
    Call<Status_Handling> registerTrip(
            @Body JsonObject trip
    );



    //Car
    @POST("/car")
    Call<Car> registerCar(
            @Body JsonObject car
    );

    @GET("/car")
    Call<ArrayList<Car>> getUserCars();

    @GET(".")
    Call<Status_Handling> Test();


    //PlaceAPi

    @GET("json")
    Call<PlaceAPi> getPlaces(
            @Query("input") String input,
            @Query("key") String key,
            @Query("language") String language,
            @Query("components") String components);
}
