package com.example.fellowtravellerbeta.data.network

import com.example.fellowtravellerbeta.data.network.request.AccountCheckRequest
import com.example.fellowtravellerbeta.data.network.request.AccountCreateRequest
import com.example.fellowtravellerbeta.data.network.response.StatusHandleResponse
import com.example.fellowtravellerbeta.data.network.response.UserInfoResponse
import retrofit2.Response

//interface ApiRepository {
//
//    suspend fun checkUserPhone(phone: String): Response<StatusHandleResponse>
//    suspend fun checkUserEmail(phone: String): Response<StatusHandleResponse>
//    suspend fun createAccount(
//        firstName: String,
//        lastName: String,
//        email: String,
//        password: String,
//        phone: String
//    ): Response<UserInfoResponse>
//
//
//    suspend fun cre(phone: String): Response<StatusHandleResponse>
//
//
//}


class ApiRepository(
    private val service: FellowTravellerApiService

) {


    suspend fun checkUserPhone(phone: String): Response<StatusHandleResponse> {
        val requestBody = AccountCheckRequest("phone", phone)
        return service.checkIfAccountInfoExist(requestBody)
    }

    suspend fun checkUserEmail(email: String): Response<StatusHandleResponse> {
        val requestBody = AccountCheckRequest("email", email)
        return service.checkIfAccountInfoExist(requestBody)
    }

    suspend fun createAccount(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        phone: String
    ): Response<UserInfoResponse> {
        val requestBody = AccountCreateRequest(firstName, lastName, email, password, phone)
        return service.registerUser(requestBody)
    }

}