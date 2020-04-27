package com.example.fellow_traveller.ClientAPI;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

import com.example.fellow_traveller.ClientAPI.Models.CarModel;
import com.example.fellow_traveller.ClientAPI.Models.StatusHandleModel;
import com.example.fellow_traveller.ClientAPI.Models.UserAuthModel;
import com.example.fellow_traveller.ClientAPI.Models.NotificationModel;
import com.example.fellow_traveller.PlaceAutocomplete.PlaceAPiModel;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public interface RetrofitAPIEndpoints {
    @POST("/auth/login")
    Call<UserAuthModel> userAuthenticate(@Body JsonObject user);


    @POST("/auth/logout")
    Call<StatusHandleModel> userLogout();


    @POST("/auth/password")
    Call<StatusHandleModel> userChangePassword(
            @Body JsonObject password
    );

    //User
    @POST("/users")
    Call<UserAuthModel> userRegister(
            @Body JsonObject user
    );
    //User
    @GET("/users")
    Call<UserAuthModel> userInfo();

    @PUT("/users")
    Call<UserAuthModel> userUpdate(
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

    @DELETE("cars")
    Call<StatusHandleModel> deleteUserCar(
            @Query("car_id") int car_id
    );





    @GET(".")
    Call<StatusHandleModel> Test();

    @GET("/notifications")
    Call<List<NotificationModel>> userNotifications();



    //PlaceAPi

    @GET("json")
    Call<PlaceAPiModel> getPlaces(
            @Query("input") String input,
            @Query("key") String key,
            @Query("language") String language,
            @Query("components") String components);
}
