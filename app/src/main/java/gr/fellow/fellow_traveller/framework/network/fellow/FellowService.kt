package gr.fellow.fellow_traveller.framework.network.fellow

import gr.fellow.fellow_traveller.framework.network.fellow.request.AccountCheckRequest
import gr.fellow.fellow_traveller.framework.network.fellow.request.AccountCreateRequest
import gr.fellow.fellow_traveller.framework.network.fellow.response.StatusHandleResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserInfoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface FellowService {

    @POST("auth/check")
    suspend fun checkIfAccountInfoExist(
        @Body request: AccountCheckRequest
    ): Response<StatusHandleResponse>



    @POST("users")
    suspend fun registerUser(
        @Body request: AccountCreateRequest
    ): Response<UserInfoResponse>



}