package com.example.fellowtravellerbeta.data.network.fellow

import com.example.fellowtravellerbeta.data.network.fellow.request.AccountCheckRequest
import com.example.fellowtravellerbeta.data.network.fellow.request.AccountCreateRequest
import com.example.fellowtravellerbeta.data.network.fellow.response.StatusHandleResponse
import com.example.fellowtravellerbeta.data.network.fellow.response.UserInfoResponse
import com.example.fellowtravellerbeta.utils.performCall
import retrofit2.Response

class ApiRepository(
    private val service: FellowTravellerApiService

) {


    suspend fun checkUserPhone(phone: String): Response<StatusHandleResponse> {
        val requestBody = AccountCheckRequest("phone", phone)
        return performCall {
             service.checkIfAccountInfoExist(requestBody)
        }
    }

    suspend fun checkUserEmail(email: String): Response<StatusHandleResponse> {
        val requestBody = AccountCheckRequest("email", email)
        return performCall {
         service.checkIfAccountInfoExist(requestBody)
        }
    }

    suspend fun createAccount(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        phone: String
    ): Response<UserInfoResponse> {
        val requestBody = AccountCreateRequest(firstName, lastName, email, password, phone)

        return performCall {
            service.registerUser(requestBody)
        }
    }

}