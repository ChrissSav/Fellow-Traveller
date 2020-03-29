package com.example.fellow_traveller.API;


import com.example.fellow_traveller.Models.User;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitService {


    @POST("user")
    Call<Status_Handling> registerUser(
            @Body User user
    );


    @POST("user/login")
    Call<User> loginUser(
            @Body JsonObject user_obj
    );
}
