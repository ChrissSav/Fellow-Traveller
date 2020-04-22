package com.example.fellow_traveller.ClientAPI;

import android.util.Log;

import com.example.fellow_traveller.ClientAPI.Models.UserAuth;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FellowTravellerAPI {
    private static Retrofit retrofit;
    private static RetrofitAPIEndpoints retrofitAPIEndpoints;

    public static void userAuthenticate(String email, String password, final UserAuthCallback userAuthCallback) {
        // TODO remove this debug line
        Log.d("Authentication", email + " " + password);
        // TODO get API url from string resource
        retrofit = new Retrofit.Builder().baseUrl("http://167.172.189.78:8000")
                .addConverterFactory(GsonConverterFactory.create()).build();
        retrofitAPIEndpoints = retrofit.create(RetrofitAPIEndpoints.class);

        JsonObject userJSON = new JsonObject();
        userJSON.addProperty("email", email);
        userJSON.addProperty("password", password);

        retrofitAPIEndpoints.userAuthenticate(userJSON).enqueue(new Callback<UserAuth>() {
            @Override
            public void onResponse(Call<UserAuth> call, Response<UserAuth> response) {
                if (!response.isSuccessful()) {
                    Log.d("Authentication", "LOGIN FAILURE!!");
                    // TODO Implement onFailure callback and standardize errors
                    userAuthCallback.onFailure();
                    return;
                }
                // TODO remove this debug line
                Log.i("Authentication", "LOGIN SUCCESS");
                userAuthCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<UserAuth> call, Throwable t) {
                Log.i("ClientAPI", "[REQUEST FAILED]: Failed to reach back-end API server.");
            }
        });
    }
}
