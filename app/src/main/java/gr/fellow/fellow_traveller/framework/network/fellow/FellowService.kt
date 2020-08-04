package gr.fellow.fellow_traveller.framework.network.fellow

import gr.fellow.fellow_traveller.framework.network.fellow.request.*
import gr.fellow.fellow_traveller.framework.network.fellow.response.CarResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.StatusHandleResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.TripResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserLoginResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

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

    @DELETE("/cars/{car_id}")
    suspend fun deleteCar(
        @Path("car_id") car_id: Int
    ): Response<StatusHandleResponse>


    //Trips
    @POST("trips")
    suspend fun addTrip(
        @Body trip: TripCreateRequest
    ): Response<TripResponse>
}
