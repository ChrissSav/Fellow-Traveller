package com.example.fellow_traveller.API;


import com.example.fellow_traveller.Models.User;
import com.example.fellow_traveller.Models.UserAuth;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitService {


    @POST("user")
    Call<UserAuth> registerUser(
            @Body JsonObject user
    );


    @POST("login")
    Call<UserAuth> loginUser(
            @Body JsonObject user_obj
    );
}
