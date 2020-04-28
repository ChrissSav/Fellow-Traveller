package com.example.fellow_traveller.PlacesAPI;

import com.example.fellow_traveller.PlacesAPI.Models.PlaceAPiModel;

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
}
