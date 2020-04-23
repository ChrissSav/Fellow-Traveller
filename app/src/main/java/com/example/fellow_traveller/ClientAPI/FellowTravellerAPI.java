package com.example.fellow_traveller.ClientAPI;

import android.util.Log;

import com.example.fellow_traveller.ClientAPI.Callbacks.UserAuthCallback;
import com.example.fellow_traveller.ClientAPI.Models.UserAuthModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONTokener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FellowTravellerAPI {
    private static Retrofit retrofit;
    private static RetrofitAPIEndpoints retrofitAPIEndpoints;

    /**
     * @param email            Email address
     * @param password         Password
     * @param userAuthCallback Callback to access the returned user object
     */
    public static void userAuthenticate(final GlobalClass cont, String email, final String password, final UserAuthCallback userAuthCallback) {
        // TODO remove this debug line
        Log.d("Authentication", email + " " + password);
        retrofit = new Retrofit.Builder().baseUrl(cont.getResources().getString(R.string.FT_API_URL))
                .addConverterFactory(GsonConverterFactory.create()).build();
        retrofitAPIEndpoints = retrofit.create(RetrofitAPIEndpoints.class);

//        JsonObject userJSON = new JsonObject();
//        userJSON.addProperty("email", email);
//        userJSON.addProperty("password", password);

        JsonObject userJSON = new JsonParser().parse("{'email':'"+ email +"', 'password':'"+ password +"'}").getAsJsonObject();
        buildJSON(new String[]{"email", "password"}, email, password);

        retrofitAPIEndpoints.userAuthenticate(userJSON).enqueue(new Callback<UserAuthModel>() {
            @Override
            public void onResponse(Call<UserAuthModel> call, Response<UserAuthModel> response) {
                if (!response.isSuccessful()) {
                    Log.d("Authentication", "LOGIN FAILURE!!");
                    // TODO Implement onFailure callback and standardize errors
                    userAuthCallback.onFailure(cont.getResources().getString(R.string.INVALID_CREDENTIALS));
                    return;
                }
                // TODO remove this debug line
                Log.i("Authentication", "LOGIN SUCCESS");
                userAuthCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<UserAuthModel> call, Throwable t) {
                Log.i("ClientAPI", "[REQUEST FAILED]: Failed to reach back-end API server.");
                userAuthCallback.onFailure(cont.getResources().getString(R.string.API_UNREACHABLE));
            }
        });

    }

    // TODO write json builder for arbitrary number of key:value elements
    // test(email: "email@example.com", "pass");
    // test(new String[](
    // HashMap<String, Integer> dicCodeToIndex;
    // find a solution


    static JsonObject buildJSON(String[] keys, String... values) {

        JsonObject jsonData = new JsonObject();
        for(String key : keys){
            Log.d("JSONKEY", key);
        }

        for (String value : values) {
            jsonData.addProperty("email", value.indexOf(value));
        }
        Log.d("JSONKEYs", jsonData.toString());

        return jsonData;
    }
}
