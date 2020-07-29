package gr.fellow.fellow_traveller.domain

import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.framework.network.fellow.response.StatusHandleResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserInfoResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserLoginResponse
import gr.fellow.fellow_traveller.framework.network.google.response.PlaceApiResponse
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity
import retrofit2.Response

interface FellowDataSource {
    suspend fun checkUserPhone(phone: String): ResultWrapper<StatusHandleResponse>

    suspend fun checkUserEmail(email: String): ResultWrapper<StatusHandleResponse>


    suspend fun registerUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        phone: String
    ): ResultWrapper<UserLoginResponse>


    suspend fun loginUser(username: String, password: String): ResultWrapper<UserLoginResponse>


    suspend fun registerUserAuth(userEntity: RegisteredUserEntity)


    suspend fun getPlaces(place: String): Response<PlaceApiResponse>

}