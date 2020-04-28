package com.example.fellow_traveller.ClientAPI;

import android.util.Log;

import com.example.fellow_traveller.ClientAPI.Callbacks.CarDeleteCallBack;
import com.example.fellow_traveller.ClientAPI.Callbacks.CarRegisterCallBack;
import com.example.fellow_traveller.ClientAPI.Callbacks.StatusCallBack;
import com.example.fellow_traveller.ClientAPI.Callbacks.TripRegisterCallBack;
import com.example.fellow_traveller.ClientAPI.Callbacks.UserAuthCallback;
import com.example.fellow_traveller.ClientAPI.Callbacks.UserCarsCallBack;
import com.example.fellow_traveller.ClientAPI.Callbacks.UserLogoutCallBack;
import com.example.fellow_traveller.ClientAPI.Callbacks.UserRegisterCallback;
import com.example.fellow_traveller.ClientAPI.Models.AddCarModel;
import com.example.fellow_traveller.ClientAPI.Models.CarModel;
import com.example.fellow_traveller.ClientAPI.Models.CreateTripModel;
import com.example.fellow_traveller.ClientAPI.Models.StatusHandleModel;
import com.example.fellow_traveller.ClientAPI.Models.UserAuthModel;
import com.example.fellow_traveller.ClientAPI.Models.UserChangePasswordModel;
import com.example.fellow_traveller.ClientAPI.Models.UserLoginModel;
import com.example.fellow_traveller.ClientAPI.Models.UserRegisterModel;
import com.example.fellow_traveller.ClientAPI.Models.UserUpdateModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;

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
                    statusCallBack.onFailure(context.getResources().getString(R.string.ERROR_API_UNAUTHORIZED));
                    return;
                }

                statusCallBack.onSuccess(context.getResources().getString(R.string.success));
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

    public static void getCars(final UserCarsCallBack userCarsCallBack) {
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

    public static void deleteCar(int car_id, final CarDeleteCallBack carDeleteCallBack) {
        retrofitAPIEndpoints.deleteUserCar(car_id).enqueue(new Callback<StatusHandleModel>() {
            @Override
            public void onResponse(Call<StatusHandleModel> call, Response<StatusHandleModel> response) {
                if (!response.isSuccessful()) {
                    try {
                        // TODO show generalized error message from errors.xml
                        if (response.code() == 401) {
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

    public static void createTrip(CreateTripModel trip, final TripRegisterCallBack tripRegisterCallBack) {
        retrofitAPIEndpoints.tripRegister(trip).enqueue(new Callback<StatusHandleModel>() {

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

}
