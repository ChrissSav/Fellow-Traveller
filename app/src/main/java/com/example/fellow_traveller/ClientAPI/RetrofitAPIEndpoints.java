package com.example.fellow_traveller.ClientAPI;

import com.example.fellow_traveller.ClientAPI.Models.AddCarModel;
import com.example.fellow_traveller.ClientAPI.Models.CarModel;
import com.example.fellow_traveller.ClientAPI.Models.CreatePassengerModel;
import com.example.fellow_traveller.ClientAPI.Models.CreateReviewModel;
import com.example.fellow_traveller.ClientAPI.Models.CreateTripModel;
import com.example.fellow_traveller.ClientAPI.Models.NotificationModel;
import com.example.fellow_traveller.ClientAPI.Models.PassengerModel;
import com.example.fellow_traveller.ClientAPI.Models.ReviewModel;
import com.example.fellow_traveller.ClientAPI.Models.SearchDestinationsModel;
import com.example.fellow_traveller.ClientAPI.Models.StatusHandleModel;
import com.example.fellow_traveller.ClientAPI.Models.TripInvolvedModel;
import com.example.fellow_traveller.ClientAPI.Models.TripModel;
import com.example.fellow_traveller.ClientAPI.Models.UserAuthModel;
import com.example.fellow_traveller.ClientAPI.Models.UserChangePasswordModel;
import com.example.fellow_traveller.ClientAPI.Models.UserLoginModel;
import com.example.fellow_traveller.ClientAPI.Models.UserRegisterModel;
import com.example.fellow_traveller.ClientAPI.Models.UserUpdateModel;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitAPIEndpoints {
    @POST("/auth/login")
    Call<UserAuthModel> userAuthenticate(@Body UserLoginModel user);


    @POST("/auth/logout")
    Call<StatusHandleModel> userLogout();


    @PUT("/auth/password")
    Call<StatusHandleModel> userChangePassword(
            @Body UserChangePasswordModel password
    );

    @POST("/auth/check")
    Call<StatusHandleModel> checkItemIfExist(
            @Body JsonObject jsonObject
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
    @GET("/trips/as_creator")
    Call<ArrayList<TripModel>> tripsAsCreator();

    @GET("/trips/as_creator")
    Call<ArrayList<TripInvolvedModel>> tripsAsCreatorTest();

    @GET("/trips/takes_part")
    Call<ArrayList<TripModel>> tripsTakesPart();

    @GET("/trips/takes_part")
    Call<ArrayList<TripInvolvedModel>> tripsTakesPartTest();


    @POST("/trips")
    Call<StatusHandleModel> tripRegister(
            @Body CreateTripModel trip
    );

    @PUT("/trips/search")
    Call<ArrayList<TripModel>> getTrips(
            @Body SearchDestinationsModel destinations,
            @Query("timestamp_min") Long timestampMin,
            @Query("timestamp_max") Long timestampMax,
            @Query("seats_min") Integer seatsMin,
            @Query("seats_max") Integer seatsMax,
            @Query("bags_min") Integer bagsMin,
            @Query("bags_max") Integer bagsMax,
            @Query("price_min") Integer priceMin,
            @Query("price_max") Integer priceMax,
            @Query("pet") Boolean hasPet,
            @Query("range") Integer range
    );

    @GET("/trips/search")
    Call<ArrayList<TripModel>> getTripsTest(
            @Query("latitude_from") float latitudeFrom,
            @Query("longitude_from") float longitudeFrom,
            @Query("latitude_to") float latitudeTo,
            @Query("longitude_to") float longitudeTo,
            @Query("timestamp_min") Long timestampMin,
            @Query("timestamp_max") Long timestampMax,
            @Query("seats_min") Integer seatsMin,
            @Query("seats_max") Integer seatsMax,
            @Query("bags_min") Integer bagsMin,
            @Query("bags_max") Integer bagsMax,
            @Query("price_min") Integer priceMin,
            @Query("price_max") Integer priceMax,
            @Query("pet") Boolean hasPet,
            @Query("range_from") Integer rangeFrom,
            @Query("range_to") Integer rangeTo
    );

    @PUT("/trips/passengers")
    Call<StatusHandleModel> addPassenger(
            @Body CreatePassengerModel passenger
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

    //Notifications

    @GET("/notifications")
    Call<ArrayList<NotificationModel>> userNotifications();


    @GET("/notifications/{notification_id}")
    Call<ArrayList<NotificationModel>> userNotifications(
            @Path("notification_id") int notification_id
    );

    @PUT("/notifications")
    Call<StatusHandleModel> updateNotifications(
            @Body JsonObject jsonObject
    );



    //Reviews

    @GET("/reviews/{user_id}")
    Call<ArrayList<ReviewModel>> userReviews(
            @Path("user_id") int user_id
    );

    @POST("/reviews")
    Call<StatusHandleModel> addUserReview(
            @Body CreateReviewModel createReviewModel
    );

}
