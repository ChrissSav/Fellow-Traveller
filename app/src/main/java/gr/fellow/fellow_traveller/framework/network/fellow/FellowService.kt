package gr.fellow.fellow_traveller.framework.network.fellow

import gr.fellow.fellow_traveller.framework.network.fellow.request.AccountCheckRequest
import gr.fellow.fellow_traveller.framework.network.fellow.request.AccountCreateRequest
import gr.fellow.fellow_traveller.framework.network.fellow.request.CarRequest
import gr.fellow.fellow_traveller.framework.network.fellow.request.LoginRequest
import gr.fellow.fellow_traveller.framework.network.fellow.response.CarResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.StatusHandleResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserLoginResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.util.*
import kotlin.collections.ArrayList

interface FellowService {
    @POST("auth/check")
    suspend fun checkIfAccountInfoExist(
        @Body request: AccountCheckRequest
    ): Response<StatusHandleResponse>


    @POST("users")
    suspend fun registerUser(
        @Body request: AccountCreateRequest
    ): Response<UserLoginResponse>


    @POST("auth/login")
    suspend fun userLogin(
        @Body user: LoginRequest
    ): Response<UserLoginResponse>


    //Car
    @GET("cars")
    suspend fun userCars(): Response<ArrayList<CarResponse>>


    @POST("cars")
    suspend fun addCar(
        @Body carRequest: CarRequest
    ): Response<CarResponse>


}
//
//    @GET("/cars")
//    fun userCars(): Call<ArrayList<CarModel?>?>?
//
//    @DELETE("/cars/{car_id}")
//    fun deleteUserCar(
//        @Path("car_id") car_id: Int
//    ): Call<StatusHandleModel?>?
//
//    @PUT("/cars")
//    fun updateCar(
//        @Body car: UpdateCarModel?
//    ): Call<CarModel?>?
//}