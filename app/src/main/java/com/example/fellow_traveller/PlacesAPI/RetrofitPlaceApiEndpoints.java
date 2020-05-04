package com.example.fellow_traveller.PlacesAPI;

import com.example.fellow_traveller.PlacesAPI.Models.PlaceAPiModel;
import com.example.fellow_traveller.PlacesAPI.Models.PlaceApiLatLonModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitPlaceApiEndpoints {


    @GET("json")
    Call<PlaceAPiModel> getPlaces(
            @Query("input") String input,
            @Query("key") String key,
            @Query("language") String language,
            @Query("components") String components);

    @GET("json")
    Call<PlaceApiLatLonModel> getPlacesLanLon(
            @Query("placeid") String placeId,
            @Query("key") String key);
}
