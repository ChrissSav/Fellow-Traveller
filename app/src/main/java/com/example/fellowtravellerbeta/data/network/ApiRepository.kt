package com.example.fellowtravellerbeta.data.network

import com.example.fellowtravellerbeta.data.network.request.AccountCheckRequest
import com.example.fellowtravellerbeta.data.network.response.StatusHandleResponse
import retrofit2.Response

interface ApiRepository {

    suspend fun checkUserPhone(phone: String): Response<StatusHandleResponse>
}


class ApiRepositoryImpl(
    private val service: FellowTravellerApiService

) : ApiRepository {


    override suspend fun checkUserPhone(phone: String): Response<StatusHandleResponse> {
        val requestBody = AccountCheckRequest("phone", phone)
        return service.checkIfAccountInfoExist(requestBody)
    }

}