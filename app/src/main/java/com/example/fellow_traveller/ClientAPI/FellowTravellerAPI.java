package com.example.fellow_traveller.ClientAPI;

import android.util.Log;

import com.example.fellow_traveller.ClientAPI.Callbacks.CarRegisterCallBack;
import com.example.fellow_traveller.ClientAPI.Callbacks.TripRegisterCallBack;
import com.example.fellow_traveller.ClientAPI.Callbacks.UserAuthCallback;
import com.example.fellow_traveller.ClientAPI.Callbacks.UserCarsCallBack;
import com.example.fellow_traveller.ClientAPI.Callbacks.UserLogoutCallBack;
import com.example.fellow_traveller.ClientAPI.Callbacks.UserRegisterCallback;
import com.example.fellow_traveller.ClientAPI.Models.CarModel;
import com.example.fellow_traveller.ClientAPI.Models.StatusHandleModel;
import com.example.fellow_traveller.ClientAPI.Models.UserAuthModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.fellow_traveller.ClientAPI.Utils.buildJSON;

public class FellowTravellerAPI {
    private static Retrofit retrofit;
    private static RetrofitAPIEndpoints retrofitAPIEndpoints;
    private static GlobalClass context;

    public FellowTravellerAPI(GlobalClass context) {
        // Pass context from the activity we were called from
        FellowTravellerAPI.context = context;
        retrofit = new Retrofit.Builder().baseUrl(context.getResources().getString(R.string.FT_API_URL))
                .addConverterFactory(GsonConverterFactory.create()).build();
        retrofitAPIEndpoints = retrofit.create(RetrofitAPIEndpoints.class);
    }

    public static void userAuthenticate(String email, final String password, final UserAuthCallback userAuthCallback) {
        JsonObject json = buildJSON(new String[]{"email", "password"}, email, password);
        retrofitAPIEndpoints.userAuthenticate(json).enqueue(new Callback<UserAuthModel>() {
            @Override
            public void onResponse(Call<UserAuthModel> call, Response<UserAuthModel> response) {
                if (!response.isSuccessful()) {
                    Log.d("Authentication", "LOGIN FAILURE!!");
                    userAuthCallback.onFailure(context.getResources().getString(R.string.INVALID_CREDENTIALS));
                    return;
                }
                userAuthCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<UserAuthModel> call, Throwable t) {
                userAuthCallback.onFailure(context.getResources().getString(R.string.API_UNREACHABLE));
            }
        });

    }

    public static void userLogout(final UserLogoutCallBack userLogoutCallBack) {
        // TODO change this method of logging out with sessionID cookie instead.
        JsonObject json = buildJSON(new String[]{"refresh_token"}, context.getCurrent_user().getRefreshToken());
        retrofitAPIEndpoints.userLogout(json).enqueue(new Callback<StatusHandleModel>() {
            @Override
            public void onResponse(Call<StatusHandleModel> call, Response<StatusHandleModel> response) {
                if (!response.isSuccessful()) {
                    try {
                        // TODO what is errorBody? the API is supposed to give a status code when logging out...
                        userLogoutCallBack.onFailure(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                // TODO should you display a message at all?
                userLogoutCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<StatusHandleModel> call, Throwable t) {
                userLogoutCallBack.onFailure(context.getResources().getString(R.string.API_UNREACHABLE));
            }
        });
    }

    public static void userRegister(String name, String surname, String email, String password, String phone, final UserRegisterCallback userRegisterCallback) {
        JsonObject json = buildJSON(new String[]{"name", "surname", "email", "password", "phone"}, name, surname, email, password, phone);
        retrofitAPIEndpoints.userRegister(json).enqueue(new Callback<UserAuthModel>() {
            @Override
            public void onResponse(Call<UserAuthModel> call, Response<UserAuthModel> response) {
                if (!response.isSuccessful()) {
                    try {
                        // TODO Display generalized error message from errors.xml
                        userRegisterCallback.onFailure(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                userRegisterCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<UserAuthModel> call, Throwable t) {
                userRegisterCallback.onFailure(context.getResources().getString(R.string.API_UNREACHABLE));
            }
        });
    }

    // TODO what about carAdd?
    public static void carRegister(String brand, String model, String plate, String color, final CarRegisterCallBack carRegisterCallBack) {
        JsonObject json = buildJSON(new String[]{"brand", "model", "plate", "color"}, brand, model, plate, color);
        retrofitAPIEndpoints.carRegister(json).enqueue(new Callback<CarModel>() {
            @Override
            public void onResponse(Call<CarModel> call, Response<CarModel> response) {
                if (!response.isSuccessful()) {
                    try {
                        // TODO show error message from errors.xml instead
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
                carRegisterCallBack.onFailure(context.getResources().getString(R.string.API_UNREACHABLE));
            }
        });
    }

    public static void getUserCars(final UserCarsCallBack userCarsCallBack) {
        retrofitAPIEndpoints.userCars().enqueue(new Callback<ArrayList<CarModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CarModel>> call, Response<ArrayList<CarModel>> response) {
                if (!response.isSuccessful()) {
                    try {
                        // TODO show generalized error message from errors.xml
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
                userCarsCallBack.onFailure(context.getResources().getString(R.string.API_UNREACHABLE));
            }
        });
    }

    // TODO what about another more sensible name, like tripCreate?
    public static void tripRegister(String dest_from, String dest_to, String pet, int max_seats, int max_bags, int car_id,
                                    float price, long timestamp, String msg, final TripRegisterCallBack tripRegisterCallBack) {
        JsonObject json = buildJSON(new String[]{
                        "dest_from", "dest_to",
                        "pet", "max_seats",
                        "max_bags", "car_id",
                        "price", "timestamp", "msg"},
                dest_from, dest_to, pet, String.valueOf(max_seats),
                String.valueOf(max_bags), String.valueOf(car_id),
                String.valueOf(price), String.valueOf(timestamp), msg);

        // TODO add a better comparison than: pet == "Επιτρέπω" ? "yes" : "no"
        // TODO get some boolean value instead, this is sloppy
        retrofitAPIEndpoints.tripRegister(json).enqueue(new Callback<StatusHandleModel>() {
            @Override
            public void onResponse(Call<StatusHandleModel> call, Response<StatusHandleModel> response) {
                if (!response.isSuccessful()) {
                    try {
                        // TODO use generic error message from errors.xml
                        tripRegisterCallBack.onFailure(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                tripRegisterCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<StatusHandleModel> call, Throwable t) {
                tripRegisterCallBack.onFailure(context.getResources().getString(R.string.API_UNREACHABLE));
            }
        });
    }
}
