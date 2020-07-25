package gr.fellow.fellow_traveller.domain

import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.framework.network.fellow.response.StatusHandleResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserInfoResponse

interface FellowDataSource {
    suspend fun checkUserPhone(phone: String): ResultWrapper<StatusHandleResponse>

    suspend fun checkUserEmail(email: String): ResultWrapper<StatusHandleResponse>


    suspend fun registerUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        phone: String
    ): ResultWrapper<UserInfoResponse>


}