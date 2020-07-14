package com.example.fellowtravellerbeta.data.network.fellow

import com.example.fellowtravellerbeta.data.network.fellow.request.AccountCheckRequest
import com.example.fellowtravellerbeta.data.network.fellow.request.AccountCreateRequest
import com.example.fellowtravellerbeta.data.network.fellow.request.TripCreateRequest
import com.example.fellowtravellerbeta.data.network.fellow.response.StatusHandleResponse
import com.example.fellowtravellerbeta.data.network.fellow.response.UserInfoResponse
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