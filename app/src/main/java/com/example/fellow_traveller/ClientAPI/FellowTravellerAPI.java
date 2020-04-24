package com.example.fellow_traveller.ClientAPI;

import android.util.Log;
import android.widget.Toast;

import com.example.fellow_traveller.API.Status_Handling;
import com.example.fellow_traveller.ClientAPI.Callbacks.CarRegisterCallBack;
import com.example.fellow_traveller.ClientAPI.Callbacks.UserAuthCallback;
import com.example.fellow_traveller.ClientAPI.Callbacks.UserCarsCallBack;
import com.example.fellow_traveller.ClientAPI.Callbacks.UserLogoutCallBack;
import com.example.fellow_traveller.ClientAPI.Models.CarModel;
import com.example.fellow_traveller.ClientAPI.Models.StatusHandleModel;
import com.example.fellow_traveller.ClientAPI.Models.UserAuthModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;
import com.example.fellow_traveller.Settings.SettingsActivity;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;

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
                userAuthCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<UserAuthModel> call, Throwable t) {
                Log.i("ClientAPI", "[REQUEST FAILED]: Failed to reach back-end API server.");
                userAuthCallback.onFailure(cont.getResources().getString(R.string.API_UNREACHABLE));
            }
        });

    }

    public static void userLogout(final GlobalClass cont, final UserLogoutCallBack userLogoutCallBack) {
        retrofit = new Retrofit.Builder().baseUrl(cont.getResources().getString(R.string.FT_API_URL))
                .addConverterFactory(GsonConverterFactory.create()).build();

        retrofitAPIEndpoints = retrofit.create(RetrofitAPIEndpoints.class);


        JsonObject userJSON = new JsonObject();
        userJSON.addProperty("refresh_token", cont.getCurrent_user().getRefreshToken());
        retrofitAPIEndpoints.userLogout(userJSON).enqueue(new Callback<StatusHandleModel>() {
            @Override
            public void onResponse(Call<StatusHandleModel> call, Response<StatusHandleModel> response) {
                if (!response.isSuccessful()) {
                    try {
                        userLogoutCallBack.onFailure(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                userLogoutCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<StatusHandleModel> call, Throwable t) {
                userLogoutCallBack.onFailure(cont.getResources().getString(R.string.API_UNREACHABLE));
            }
        });
    }


    public static void userCars(final GlobalClass cont, String brand, String model,String plate, String color, final CarRegisterCallBack carRegisterCallBack) {
        retrofit = new Retrofit.Builder().baseUrl(cont.getResources().getString(R.string.FT_API_URL)).client(cont.getOkHttpClient().build())
                .addConverterFactory(GsonConverterFactory.create()).build();

        retrofitAPIEndpoints = retrofit.create(RetrofitAPIEndpoints.class);


        JsonObject car_object = new JsonObject();
        car_object.addProperty("brand", brand);
        car_object.addProperty("model", model);
        car_object.addProperty("plate", plate);
        car_object.addProperty("color", color);

        retrofitAPIEndpoints.carRegister(car_object).enqueue(new Callback<CarModel>() {
            @Override
            public void onResponse(Call<CarModel> call, Response<CarModel> response) {
                if (!response.isSuccessful()) {
                    try {
                        carRegisterCallBack.onFailure(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                carRegisterCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<CarModel> call, Throwable t) {
                carRegisterCallBack.onFailure(cont.getResources().getString(R.string.API_UNREACHABLE));
            }
        });

    }










    public static void carRegister(final GlobalClass cont, final UserCarsCallBack userCarsCallBack) {
        retrofit = new Retrofit.Builder().baseUrl(cont.getResources().getString(R.string.FT_API_URL)).client(cont.getOkHttpClient().build())
                .addConverterFactory(GsonConverterFactory.create()).build();

        retrofitAPIEndpoints = retrofit.create(RetrofitAPIEndpoints.class);

        retrofitAPIEndpoints.userCars().enqueue(new Callback<ArrayList<CarModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CarModel>> call, Response<ArrayList<CarModel>> response) {
                if (!response.isSuccessful()) {
                    try {
                        userCarsCallBack.onFailure(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                userCarsCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<CarModel>> call, Throwable t) {
                userCarsCallBack.onFailure(cont.getResources().getString(R.string.API_UNREACHABLE));
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
