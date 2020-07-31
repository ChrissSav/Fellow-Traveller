package gr.fellow.fellow_traveller.data

import gr.fellow.fellow_traveller.framework.network.google.response.PlaceApiResponse
import gr.fellow.fellow_traveller.framework.network.fellow.request.AccountCheckRequest
import gr.fellow.fellow_traveller.framework.network.fellow.request.AccountCreateRequest
import gr.fellow.fellow_traveller.framework.network.fellow.request.LoginRequest
import gr.fellow.fellow_traveller.framework.network.fellow.response.CarResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.StatusHandleResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserLoginResponse
import retrofit2.Response

interface FellowRepository {

    suspend fun checkField(accountCheckRequest: AccountCheckRequest): ResultWrapper<StatusHandleResponse>

    suspend fun registerUser(registerUserRequest: AccountCreateRequest): ResultWrapper<UserLoginResponse>

    suspend fun loginUser(loginRequest: LoginRequest): ResultWrapper<UserLoginResponse>

    suspend fun getPlace(place: String): Response<PlaceApiResponse>


    suspend fun getCars(): ResultWrapper<ArrayList<CarResponse>>
}