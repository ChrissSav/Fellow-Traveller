package com.example.fellow_traveller.PlacesAPI;

import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.PlacesAPI.CallBack.PlaceApiCallBack;
import com.example.fellow_traveller.PlacesAPI.CallBack.PlaceApiResultCallBack;
import com.example.fellow_traveller.PlacesAPI.Models.PlaceAPiModel;
import com.example.fellow_traveller.PlacesAPI.Models.PlaceApiLatLonModel;
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
    private static String KEY_API;

    public PlaceApiConnection(GlobalClass context) {

        PlaceApiConnection.context = context;
        retrofit = new Retrofit.Builder().baseUrl(context.getResources().getString(R.string.PLACE_URL))
                    .addConverterFactory(GsonConverterFactory.create()).build();

        retrofitPlaceApiEndpoints = retrofit.create(RetrofitPlaceApiEndpoints.class);
    }
    public PlaceApiConnection(GlobalClass context, boolean details) {
        PlaceApiConnection.context = context;
        KEY_API = context.getResources().getString(R.string.PLACE_KEY);

        if (details) {
            retrofit = new Retrofit.Builder().baseUrl(context.getResources().getString(R.string.PLACE_DETAIL_URL))
                    .addConverterFactory(GsonConverterFactory.create()).build();
        } else {
            retrofit = new Retrofit.Builder().baseUrl(context.getResources().getString(R.string.PLACE_URL))
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }

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

    public static void getLatLonFromPlace(String placeId, final PlaceApiResultCallBack placeApiResultCallBack) {
        retrofitPlaceApiEndpoints.getPlacesLanLon(placeId, KEY_API).enqueue(new Callback<PlaceApiLatLonModel>() {
            @Override
            public void onResponse(Call<PlaceApiLatLonModel> call, Response<PlaceApiLatLonModel> response) {
                if (!response.isSuccessful()) {
                    try {
                        placeApiResultCallBack.onFailure(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                placeApiResultCallBack.onSuccess(response.body().getResult());
            }

            @Override
            public void onFailure(Call<PlaceApiLatLonModel> call, Throwable t) {
                placeApiResultCallBack.onFailure(context.getResources().getString(R.string.ERROR_API_UNREACHABLE));
            }
        });
    }

}
