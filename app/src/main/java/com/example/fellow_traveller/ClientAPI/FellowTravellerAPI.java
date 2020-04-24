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
import com.google.gson.JsonParser;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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





    public static void userRegister(final GlobalClass cont,String name, String surname,String email,String password,String phone,final UserRegisterCallback userRegisterCallback) {
        retrofit = new Retrofit.Builder().baseUrl(cont.getResources().getString(R.string.FT_API_URL))
                .addConverterFactory(GsonConverterFactory.create()).build();

        retrofitAPIEndpoints = retrofit.create(RetrofitAPIEndpoints.class);


        JsonObject user_obj = new JsonObject();
        user_obj.addProperty("name", name);
        user_obj.addProperty("surname", surname);
        user_obj.addProperty("email", email);
        user_obj.addProperty("password", password);
        user_obj.addProperty("phone",phone);

        retrofitAPIEndpoints.userRegister(user_obj).enqueue(new Callback<UserAuthModel>() {
            @Override
            public void onResponse(Call<UserAuthModel> call, Response<UserAuthModel> response) {
                if (!response.isSuccessful()) {
                    try {
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
                userRegisterCallback.onFailure(cont.getResources().getString(R.string.API_UNREACHABLE));
            }
        });
    }






    public static void carRegister(final GlobalClass cont, String brand, String model,String plate, String color, final CarRegisterCallBack carRegisterCallBack) {
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



    public static void getUserCars(final GlobalClass cont, final UserCarsCallBack userCarsCallBack) {
        retrofit = buildRetrofitWithClient(cont);

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



    //Trip


    public static void tripRegister(final GlobalClass cont, String dest_from,String dest_to, String pet, int max_seats, int max_bags, int car_id,
                                    float price,long timestamp,String msg, final TripRegisterCallBack tripRegisterCallBack) {
        retrofit = buildRetrofitWithClient(cont);

        retrofitAPIEndpoints = retrofit.create(RetrofitAPIEndpoints.class);


        JsonObject trip_object = new JsonObject();
        trip_object.addProperty("dest_from", dest_from);
        trip_object.addProperty("dest_to", dest_to);
        trip_object.addProperty("pet", pet == "Επιτρέπω" ? "yes" : "no");
        trip_object.addProperty("max_seats", max_seats);
        trip_object.addProperty("max_bags", max_bags);
        trip_object.addProperty("car_id", car_id);
        trip_object.addProperty("price", price);
        trip_object.addProperty("timestamp", timestamp);
        trip_object.addProperty("msg", msg);

        retrofitAPIEndpoints.tripRegister(trip_object).enqueue(new Callback<StatusHandleModel>() {
            @Override
            public void onResponse(Call<StatusHandleModel> call, Response<StatusHandleModel> response) {
                if (!response.isSuccessful()) {
                    try {
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
                tripRegisterCallBack.onFailure(cont.getResources().getString(R.string.API_UNREACHABLE));
            }
        });
    }

    public static Retrofit buildRetrofitWithClient(GlobalClass globalClass){
       return new Retrofit.Builder().baseUrl(globalClass.getResources().getString(R.string.FT_API_URL)).client(globalClass.getOkHttpClient().build())
                .addConverterFactory(GsonConverterFactory.create()).build();

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
