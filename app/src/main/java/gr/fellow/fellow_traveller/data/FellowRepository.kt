package gr.fellow.fellow_traveller.data

import gr.fellow.fellow_traveller.framework.network.fellow.request.AccountCheckRequest
import gr.fellow.fellow_traveller.framework.network.fellow.request.AccountCreateRequest
import gr.fellow.fellow_traveller.framework.network.fellow.response.StatusHandleResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserInfoResponse
import retrofit2.Response

interface FellowRepository {

    suspend fun checkField(accountCheckRequest: AccountCheckRequest): ResultWrapper<StatusHandleResponse>

    suspend fun registerUser(registerUserRequest: AccountCreateRequest): ResultWrapper<UserInfoResponse>



}