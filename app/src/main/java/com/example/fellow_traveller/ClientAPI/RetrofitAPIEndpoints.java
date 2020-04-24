package com.example.fellow_traveller.ClientAPI;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import com.example.fellow_traveller.API.Status_Handling;
import com.example.fellow_traveller.ClientAPI.Models.CarModel;
import com.example.fellow_traveller.ClientAPI.Models.StatusHandleModel;
import com.example.fellow_traveller.ClientAPI.Models.UserAuthModel;
import com.example.fellow_traveller.ClientAPI.Models.Car;
import com.example.fellow_traveller.Models.NotificationModel;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public interface RetrofitAPIEndpoints {
    @POST("/auth/login")
    Call<UserAuthModel> userAuthenticate(@Body JsonObject user);


    @POST("auth/logout")
    Call<StatusHandleModel> userLogout(
            @Body JsonObject token
    );

    //User
    @POST("user")
    Call<UserAuthModel> userRegister(
            @Body JsonObject user
    );


    //Trip
    @POST("/trips")
    Call<StatusHandleModel> tripRegister(
            @Body JsonObject trip
    );



    //Car
    @POST("/cars")
    Call<CarModel> carRegister(
            @Body JsonObject car
    );

    @GET("/cars")
    Call<ArrayList<CarModel>> userCars();

    @GET(".")
    Call<StatusHandleModel> Test();

    @GET("/notifications")
    Call<List<NotificationModel>> userNotifications();
}
