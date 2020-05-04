package com.example.fellow_traveller.ClientAPI;

import android.util.Log;

import com.example.fellow_traveller.ClientAPI.Callbacks.CarDeleteCallBack;
import com.example.fellow_traveller.ClientAPI.Callbacks.CarRegisterCallBack;
import com.example.fellow_traveller.ClientAPI.Callbacks.SearchTripsCallback;
import com.example.fellow_traveller.ClientAPI.Callbacks.StatusCallBack;
import com.example.fellow_traveller.ClientAPI.Callbacks.TripRegisterCallBack;
import com.example.fellow_traveller.ClientAPI.Callbacks.UserAuthCallback;
import com.example.fellow_traveller.ClientAPI.Callbacks.UserCarsCallBack;
import com.example.fellow_traveller.ClientAPI.Callbacks.UserLogoutCallBack;
import com.example.fellow_traveller.ClientAPI.Callbacks.UserRegisterCallback;
import com.example.fellow_traveller.ClientAPI.Models.AddCarModel;
import com.example.fellow_traveller.ClientAPI.Models.CarModel;
import com.example.fellow_traveller.ClientAPI.Models.CreatePassengerModel;
import com.example.fellow_traveller.ClientAPI.Models.CreateTripModel;
import com.example.fellow_traveller.ClientAPI.Models.CreateTripModelTest;
import com.example.fellow_traveller.ClientAPI.Models.ErrorResponseModel;
import com.example.fellow_traveller.ClientAPI.Models.StatusHandleModel;
import com.example.fellow_traveller.ClientAPI.Models.TripModel;
import com.example.fellow_traveller.ClientAPI.Models.UserAuthModel;
import com.example.fellow_traveller.ClientAPI.Models.UserChangePasswordModel;
import com.example.fellow_traveller.ClientAPI.Models.UserLoginModel;
import com.example.fellow_traveller.ClientAPI.Models.UserRegisterModel;
import com.example.fellow_traveller.ClientAPI.Models.UserUpdateModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

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

    public static void userLogin(UserLoginModel user, final UserAuthCallback userAuthCallback) {
        retrofitAPIEndpoints.userAuthenticate(user).enqueue(new Callback<UserAuthModel>() {
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
                userAuthCallback.onSuccess(userAuthModel);
            }

            @Override
            public void onFailure(Call<UserAuthModel> call, Throwable t) {
                userAuthCallback.onFailure(context.getResources().getString(R.string.ERROR_API_UNAUTHORIZED));
            }
        });

    }

    public static void checkFieldIfExist(String item, String value, final StatusCallBack statusCallBack) {
        JsonObject json = new JsonObject();
        json.addProperty("item", item);
        json.addProperty("value", value);

        retrofitAPIEndpoints.checkItemIfExist(json).enqueue(new Callback<StatusHandleModel>() {
            @Override
            public void onResponse(Call<StatusHandleModel> call, Response<StatusHandleModel> response) {
                if (!response.isSuccessful()) {
                    statusCallBack.onFailure("");
                    return;
                }

                statusCallBack.onSuccess("");
            }

            @Override
            public void onFailure(Call<StatusHandleModel> call, Throwable t) {
                statusCallBack.onFailure("");

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

    public static void userRegister(UserRegisterModel user, final UserRegisterCallback userRegisterCallback) {
        retrofitAPIEndpoints.userRegister(user).enqueue(new Callback<UserAuthModel>() {
            @Override
            public void onResponse(Call<UserAuthModel> call, Response<UserAuthModel> response) {
                if (!response.isSuccessful()) {
                    ErrorResponseModel errorResponseModel = getModelFromResponseErrorBody(response);
                    switch (errorResponseModel.getDetail().getStatusCode()) {
                        case 200:
                            userRegisterCallback.onFailure(context.getResources().getString(R.string.ERROR_PHONE_ALREADY_EXISTS));
                            break;
                        case 201:
                            userRegisterCallback.onFailure(context.getResources().getString(R.string.ERROR_EMAIL_ALREADY_EXISTS));
                            break;
                        default:
                            userRegisterCallback.onFailure(context.getResources().getString(R.string.ERROR_API_UNREACHABLE));
                            break;
                    }

                    return;
                }
                String key = response.headers().get("Set-Cookie").split(";")[0];
                UserAuthModel userAuthModel = response.body();
                userAuthModel.setSessionId(key);
                userRegisterCallback.onSuccess(userAuthModel);
            }

            @Override
            public void onFailure(Call<UserAuthModel> call, Throwable t) {
                userRegisterCallback.onFailure(context.getResources().getString(R.string.ERROR_API_UNAUTHORIZED));
            }
        });
    }

    public static void userChangePassword(UserChangePasswordModel user, final StatusCallBack statusCallBack) {

        retrofitAPIEndpoints.userChangePassword(user).enqueue(new Callback<StatusHandleModel>() {
            @Override
            public void onResponse(Call<StatusHandleModel> call, Response<StatusHandleModel> response) {
                if (!response.isSuccessful()) {
                    Log.d("Authentication", "LOGIN FAILURE!!");

                    ErrorResponseModel errorResponseModel = getModelFromResponseErrorBody(response);
                    switch (errorResponseModel.getDetail().getStatusCode()) {
                        case 101:
                            statusCallBack.onFailure(context.getResources().getString(R.string.ERROR_OLD_PASSWORD_CANNOT_BE_SAME_AS_NEW_PASSWORD));
                            break;
                        case 102:
                            statusCallBack.onFailure(context.getResources().getString(R.string.ERROR_PASSWORD_CHANGE_INVALID_CURRENT_PASSWORD));
                            break;
                        default:
                            statusCallBack.onFailure(context.getResources().getString(R.string.ERROR_API_UNREACHABLE));
                            break;
                    }
                    return;
                }

                statusCallBack.onSuccess(context.getResources().getString(R.string.PASSWORD_CHANGED_SUCCESSFULLY));
            }

            @Override
            public void onFailure(Call<StatusHandleModel> call, Throwable t) {
                statusCallBack.onFailure(context.getResources().getString(R.string.ERROR_API_UNREACHABLE));
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

    public static void updateUserInfo(UserUpdateModel user, final UserAuthCallback userAuthCallback) {
        retrofitAPIEndpoints.userUpdate(user).enqueue(new Callback<UserAuthModel>() {
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

    public static void addCar(AddCarModel car, final CarRegisterCallBack carRegisterCallBack) {
        retrofitAPIEndpoints.carRegister(car).enqueue(new Callback<CarModel>() {
            @Override
            public void onResponse(Call<CarModel> call, Response<CarModel> response) {
                if (!response.isSuccessful()) {

                    ErrorResponseModel errorResponseModel = getModelFromResponseErrorBody(response);
                    switch (errorResponseModel.getDetail().getStatusCode()) {
                        case 300:
                            carRegisterCallBack.onFailure(context.getResources().getString(R.string.ERROR_PLATE__ALREADY_EXISTS));
                            break;
                        default:
                            carRegisterCallBack.onFailure(context.getResources().getString(R.string.ERROR_API_UNREACHABLE));
                            break;
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

    public static void getCars(final UserCarsCallBack userCarsCallBack) {
        retrofitAPIEndpoints.userCars().enqueue(new Callback<ArrayList<CarModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CarModel>> call, Response<ArrayList<CarModel>> response) {
                if (!response.isSuccessful()) {

                    userCarsCallBack.onFailure(context.getResources().getString(R.string.ERROR_API_UNAUTHORIZED));


                    return;
                }
                userCarsCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<CarModel>> call, Throwable t) {
                userCarsCallBack.onFailure(context.getResources().getString(R.string.ERROR_API_UNREACHABLE));
            }
        });
    }

    public static void deleteCar(int car_id, final CarDeleteCallBack carDeleteCallBack) {
        retrofitAPIEndpoints.deleteUserCar(car_id).enqueue(new Callback<StatusHandleModel>() {
            @Override
            public void onResponse(Call<StatusHandleModel> call, Response<StatusHandleModel> response) {
                if (!response.isSuccessful()) {
                    ErrorResponseModel errorResponseModel = getModelFromResponseErrorBody(response);
                    switch (errorResponseModel.getDetail().getStatusCode()) {
                        case 301:
                            carDeleteCallBack.onFailure(context.getResources().getString(R.string.ERROR_CAR_NOT_BELONG_TO_USER));
                            break;
                        default:
                            carDeleteCallBack.onFailure(context.getResources().getString(R.string.ERROR_API_UNAUTHORIZED));
                            break;
                    }
                    return;
                }
                carDeleteCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<StatusHandleModel> call, Throwable t) {
                carDeleteCallBack.onFailure(context.getResources().getString(R.string.ERROR_API_UNREACHABLE));
            }
        });
    }

    public static void createTrip(CreateTripModelTest trip,
                                  final TripRegisterCallBack tripRegisterCallBack) {
        retrofitAPIEndpoints.tripRegister(trip).enqueue(new Callback<StatusHandleModel>() {

            @Override
            public void onResponse(Call<StatusHandleModel> call, Response<StatusHandleModel> response) {
                if (!response.isSuccessful()) {

                    ErrorResponseModel errorResponseModel = getModelFromResponseErrorBody(response);
                    switch (errorResponseModel.getDetail().getStatusCode()) {
                        case 402:
                            tripRegisterCallBack.onFailure(context.getResources().getString(R.string.ERROR_CAR_NOT_BELONG_TO_USER));
                            break;
                        default:
                            tripRegisterCallBack.onFailure(context.getResources().getString(R.string.ERROR_API_UNAUTHORIZED));
                            break;
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

    public static void getTrips(String destTo, String destFrom, Integer timestampMin, Integer timestampMax,
                                Integer seatsMin, Integer seatsMax, Integer bagsMin, Integer bagsMax,
                                Integer priceMin, Integer priceMax, Boolean hasPet, final SearchTripsCallback searchTripsCallback) {
        retrofitAPIEndpoints.getTrips(destTo, destFrom, timestampMin, timestampMax, seatsMin,
                seatsMax, bagsMin, bagsMax, priceMin,
                priceMax, hasPet).enqueue(new Callback<ArrayList<TripModel>>() {
            @Override
            public void onResponse(Call<ArrayList<TripModel>> call, Response<ArrayList<TripModel>> response) {
                searchTripsCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<TripModel>> call, Throwable t) {
                searchTripsCallback.onFailure("No trips available.");
            }
        });
    }

    public static void addPassengerToTrip(CreatePassengerModel passenger, final StatusCallBack statusCallBack) {
        retrofitAPIEndpoints.addPassenger(passenger).enqueue(new Callback<StatusHandleModel>() {
            @Override
            public void onResponse(Call<StatusHandleModel> call, Response<StatusHandleModel> response) {
                if (!response.isSuccessful()) {
                    ErrorResponseModel errorResponseModel = getModelFromResponseErrorBody(response);
                    switch (errorResponseModel.getDetail().getStatusCode()) {
                        case 400:
                            statusCallBack.onFailure(context.getResources().getString(R.string.ERROR_TRIP_NOT_FOUND));
                            break;
                        case 403:
                            statusCallBack.onFailure(context.getResources().getString(R.string.ERROR_TRIP_USER_ALREADY_PASSENGER));
                            break;
                        case 404:
                            statusCallBack.onFailure(context.getResources().getString(R.string.ERROR_TRIP_USER_IS_CREATOR));
                            break;
                        case 405:
                            statusCallBack.onFailure(context.getResources().getString(R.string.ERROR_TRIP_NOT_AVAILABLE_SEATS));
                            break;
                        case 406:
                            statusCallBack.onFailure(context.getResources().getString(R.string.ERROR_TRIP_NOT_AVAILABLE_LUGGAGE));
                            break;
                        case 407:
                            statusCallBack.onFailure(context.getResources().getString(R.string.ERROR_TRIP_CHECK_PET_ACCEPTS));
                            break;
                        default:
                            statusCallBack.onFailure(context.getResources().getString(R.string.ERROR_API_UNREACHABLE));
                            break;
                    }
                    return;
                }
                statusCallBack.onSuccess(response.body().getMsg());

            }

            @Override
            public void onFailure(Call<StatusHandleModel> call, Throwable t) {
                statusCallBack.onFailure(context.getResources().getString(R.string.ERROR_API_UNAUTHORIZED));
            }
        });
    }

    public static ErrorResponseModel getModelFromResponseErrorBody(Response response) {
        Gson gson = new Gson();
        return gson.fromJson(response.errorBody().charStream(), ErrorResponseModel.class);
    }

}
