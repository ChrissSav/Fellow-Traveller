package com.example.fellow_traveller.PlacesAPI;

import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.PlacesAPI.CallBack.PlaceApiCallBack;
import com.example.fellow_traveller.PlacesAPI.Models.PlaceAPiModel;
import com.example.fellow_traveller.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaceApiConnection {
    private static Retrofit retrofit;
    private static RetrofitPlaceApiEndpoints retrofitPlaceApiEndpoints;
    private static GlobalClass context;

    public PlaceApiConnection(GlobalClass context) {

        PlaceApiConnection.context = context;
        retrofit = new Retrofit.Builder().baseUrl(context.getResources().getString(R.string.PLACE_URL))
                    .addConverterFactory(GsonConverterFactory.create()).build();

        retrofitPlaceApiEndpoints = retrofit.create(RetrofitPlaceApiEndpoints.class);
    }

    public static void getPlaces(String place, final PlaceApiCallBack placeApiCallBack) {
        String key = context.getResources().getString(R.string.PLACE_KEY);
        String language = context.getResources().getString(R.string.PLACE_LANGUAGE);
        String country = "country:gr";

        retrofitPlaceApiEndpoints.getPlaces(place,key,language,country).enqueue(new Callback<PlaceAPiModel>() {
            @Override
            public void onResponse(Call<PlaceAPiModel> call, Response<PlaceAPiModel> response) {
                if (!response.isSuccessful()) {
                    try {
                        // TODO use generic error message from errors.xml
                        placeApiCallBack.onFailure(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                placeApiCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<PlaceAPiModel> call, Throwable t) {
                placeApiCallBack.onFailure(context.getResources().getString(R.string.ERROR_API_UNREACHABLE));
            }
        });
    }

}
