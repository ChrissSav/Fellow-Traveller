package com.example.fellow_traveller.API;

import com.example.fellow_traveller.Models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitService {



    @POST("user")
    Call<Status_Handling> registerUser(
            @Body User user
    );
}
