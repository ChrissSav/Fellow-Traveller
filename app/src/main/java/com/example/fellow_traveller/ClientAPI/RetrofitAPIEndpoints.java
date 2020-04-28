package com.example.fellow_traveller.ClientAPI;

import com.example.fellow_traveller.ClientAPI.Models.AddCarModel;
import com.example.fellow_traveller.ClientAPI.Models.CarModel;
import com.example.fellow_traveller.ClientAPI.Models.CreateTripModel;
import com.example.fellow_traveller.ClientAPI.Models.NotificationModel;
import com.example.fellow_traveller.ClientAPI.Models.StatusHandleModel;
import com.example.fellow_traveller.ClientAPI.Models.UserAuthModel;
import com.example.fellow_traveller.ClientAPI.Models.UserChangePasswordModel;
import com.example.fellow_traveller.ClientAPI.Models.UserLoginModel;
import com.example.fellow_traveller.ClientAPI.Models.UserRegisterModel;
import com.example.fellow_traveller.ClientAPI.Models.UserUpdateModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitAPIEndpoints {
    @POST("/auth/login")
    Call<UserAuthModel> userAuthenticate(@Body UserLoginModel user);


    @POST("/auth/logout")
    Call<StatusHandleModel> userLogout();


    @PUT("/auth/password")
    Call<StatusHandleModel> userChangePassword(
            @Body UserChangePasswordModel password
    );

    //User
    @POST("/users")
    Call<UserAuthModel> userRegister(
            @Body UserRegisterModel user
    );

    //User
    @GET("/users")
    Call<UserAuthModel> userInfo();

    @PUT("/users")
    Call<UserAuthModel> userUpdate(
            @Body UserUpdateModel user
    );


    //Trip
    @POST("/trips")
    Call<StatusHandleModel> tripRegister(
            @Body CreateTripModel trip
    );


    //Car
    @POST("/cars")
    Call<CarModel> carRegister(
            @Body AddCarModel car
    );

    @GET("/cars")
    Call<ArrayList<CarModel>> userCars();

    @DELETE("cars/{car_id}")
    Call<StatusHandleModel> deleteUserCar(
            @Path("car_id") int car_id
    );


    @GET(".")
    Call<StatusHandleModel> Test();

    @GET("/notifications")
    Call<List<NotificationModel>> userNotifications();

}
