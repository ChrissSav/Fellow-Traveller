package com.example.fellowtravellerbeta.data.network

import com.example.fellowtravellerbeta.data.network.request.AccountCheckRequest
import com.example.fellowtravellerbeta.data.network.request.AccountCreateRequest
import com.example.fellowtravellerbeta.data.network.request.TripCreateRequest
import com.example.fellowtravellerbeta.data.network.response.StatusHandleResponse
import com.example.fellowtravellerbeta.data.network.response.UserInfoResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface FellowTravellerApiService {



    @POST("auth/check")
    suspend fun checkIfAccountInfoExist(
        @Body request: AccountCheckRequest
    ): Response<StatusHandleResponse>


    @POST("users")
    suspend fun registerUser(
        @Body request: AccountCreateRequest
    ): Response<UserInfoResponse>



    @POST("users")
    suspend fun tripRegister(
        @Body request: TripCreateRequest
    ): Response<StatusHandleResponse>



}