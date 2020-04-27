package com.example.fellow_traveller.ClientAPI;

import android.util.Log;

import com.example.fellow_traveller.ClientAPI.Callbacks.CarDeleteCallBack;
import com.example.fellow_traveller.ClientAPI.Callbacks.CarRegisterCallBack;
import com.example.fellow_traveller.ClientAPI.Callbacks.PlaceApiCallBack;
import com.example.fellow_traveller.ClientAPI.Callbacks.TripRegisterCallBack;
import com.example.fellow_traveller.ClientAPI.Callbacks.UserAuthCallback;
import com.example.fellow_traveller.ClientAPI.Callbacks.UserCarsCallBack;
import com.example.fellow_traveller.ClientAPI.Callbacks.UserLogoutCallBack;
import com.example.fellow_traveller.ClientAPI.Callbacks.UserRegisterCallback;
import com.example.fellow_traveller.ClientAPI.Models.CarModel;
import com.example.fellow_traveller.ClientAPI.Models.StatusHandleModel;
import com.example.fellow_traveller.ClientAPI.Models.UserAuthModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.PlaceAutocomplete.PlaceAPiModel;
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
        if (context.getCurrentUser() == null) {
            Log.d("FellowTravellerAPI", " with out client");

            retrofit = new Retrofit.Builder().baseUrl(context.getResources().getString(R.string.API_BASE_URL))
                    .addConverterFactory(GsonConverterFactory.create()).build();
        } else {
            Log.d("FellowTravellerAPI", " with  client");

            retrofit = new Retrofit.Builder().baseUrl(context.getResources().getString(R.string.API_BASE_URL))
                    .client(context.getOkHttpClient().build())
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        retrofitAPIEndpoints = retrofit.create(RetrofitAPIEndpoints.class);
    }

    public static void userAuthenticate(String email, final String password, final UserAuthCallback userAuthCallback) {
        JsonObject json = buildJSON(new String[]{"email", "password"}, email, password);

        retrofitAPIEndpoints.userAuthenticate(json).enqueue(new Callback<UserAuthModel>() {
            @Override
            public void onResponse(Call<UserAuthModel> call, Response<UserAuthModel> response) {
                if (!response.isSuccessful()) {
                    Log.d("Authentication", "LOGIN FAILURE!!");
                    userAuthCallback.onFailure(context.getResources().getString(R.string.ERROR_INVALID_CREDENTIALS));
                    return;
                }

                String key = response.headers().get("Set-Cookie").split(";")[0];
                UserAuthModel userAuthModel = response.body();
                userAuthModel.setSessionId(key);
                userAuthCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<UserAuthModel> call, Throwable t) {
                userAuthCallback.onFailure(context.getResources().getString(R.string.ERROR_API_UNAUTHORIZED));
            }
        });

    }

    public static void userLogout(final UserLogoutCallBack userLogoutCallBack) {
        // TODO change this method of logging out with sessionID cookie instead.
        retrofitAPIEndpoints.userLogout().enqueue(new Callback<StatusHandleModel>() {
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
                userLogoutCallBack.onFailure(context.getResources().getString(R.string.ERROR_API_UNAUTHORIZED));
            }
        });
    }

    public static void userRegister(String firstName, String lastName, String email, String password, String phone, final UserRegisterCallback userRegisterCallback) {
        JsonObject json = buildJSON(new String[]{"first_name", "last_name", "email", "password", "phone"}, firstName, lastName, email, password, phone);
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
                String key = response.headers().get("Set-Cookie").split(";")[0];
                UserAuthModel userAuthModel = response.body();
                userAuthModel.setSessionId(key);
                userRegisterCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<UserAuthModel> call, Throwable t) {
                userRegisterCallback.onFailure(context.getResources().getString(R.string.ERROR_API_UNAUTHORIZED));
            }
        });
    }


    public static void getUserInfo(final UserAuthCallback userAuthCallback) {
        retrofitAPIEndpoints.userInfo().enqueue(new Callback<UserAuthModel>() {
            @Override
            public void onResponse(Call<UserAuthModel> call, Response<UserAuthModel> response) {
                if (!response.isSuccessful()) {
                    try {
                        // TODO Display generalized error message from errors.xml
                        userAuthCallback.onFailure(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                userAuthCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<UserAuthModel> call, Throwable t) {
                userAuthCallback.onFailure(context.getResources().getString(R.string.ERROR_API_UNAUTHORIZED));
            }
        });
    }

    public static void updateUserInfo(String firstName,String lastName,String picture,String aboutMe,String phone,final UserAuthCallback userAuthCallback) {
        JsonObject json = buildJSON(new String[]{"first_name", "last_name", "picture", "about_me", "phone"}, firstName, lastName, picture, aboutMe, phone);

        retrofitAPIEndpoints.userUpdate(json).enqueue(new Callback<UserAuthModel>() {
            @Override
            public void onResponse(Call<UserAuthModel> call, Response<UserAuthModel> response) {
                if (!response.isSuccessful()) {
                    try {
                        // TODO Display generalized error message from errors.xml
                        userAuthCallback.onFailure(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                userAuthCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<UserAuthModel> call, Throwable t) {
                userAuthCallback.onFailure(context.getResources().getString(R.string.ERROR_API_UNAUTHORIZED));
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
                carRegisterCallBack.onFailure(context.getResources().getString(R.string.ERROR_API_UNAUTHORIZED));
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
                userCarsCallBack.onFailure(context.getResources().getString(R.string.ERROR_API_UNAUTHORIZED));
            }
        });
    }

    public static void deleteUserCar(int car_id, final CarDeleteCallBack carDeleteCallBack) {
        retrofitAPIEndpoints.deleteUserCar(car_id).enqueue(new Callback<StatusHandleModel>() {
            @Override
            public void onResponse(Call<StatusHandleModel> call, Response<StatusHandleModel> response) {
                if (!response.isSuccessful()) {
                    try {
                        // TODO show generalized error message from errors.xml
                        if( response.code()==401){
                            carDeleteCallBack.onFailure(context.getResources().getString(R.string.ERROR_API_UNAUTHORIZED));
                            return;
                        }
                        carDeleteCallBack.onFailure(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                carDeleteCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<StatusHandleModel> call, Throwable t) {
                carDeleteCallBack.onFailure(context.getResources().getString(R.string.ERROR_API_UNAUTHORIZED));
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
                tripRegisterCallBack.onFailure(context.getResources().getString(R.string.ERROR_API_UNAUTHORIZED));
            }
        });
    }

    public static void getPlaces(String place, final PlaceApiCallBack placeApiCallBack) {
        String key = context.getResources().getString(R.string.PLACE_KEY);
        String language = context.getResources().getString(R.string.PLACE_LANGUAGE);
        String country = "country:gr";

        Retrofit retrofit = new Retrofit.Builder().baseUrl(context.getResources().getString(R.string.PLACE_URL))
                .addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPIEndpoints retrofitService = retrofit.create(RetrofitAPIEndpoints.class);

        Call<PlaceAPiModel> call = retrofitService.getPlaces(place, key, language, country);
        call.enqueue(new Callback<PlaceAPiModel>() {
            @Override
            public void onResponse(Call<PlaceAPiModel> call, Response<PlaceAPiModel> response) {
                if (!response.isSuccessful()) {
                    try {
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
                placeApiCallBack.onFailure(t.getMessage());

            }
        });
    }

}
