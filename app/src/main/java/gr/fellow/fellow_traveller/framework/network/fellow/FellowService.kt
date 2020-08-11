package gr.fellow.fellow_traveller.framework.network.fellow

import gr.fellow.fellow_traveller.framework.network.fellow.request.*
import gr.fellow.fellow_traveller.framework.network.fellow.response.CarResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.StatusHandleResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.TripResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserLoginResponse
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


    @GET("trips")
    suspend fun getTripsAs(
        @Query("type_as") type: String
    ): Response<MutableList<TripResponse>>

    @GET("trips/search")
    suspend fun searchTrips(
        @Query("latitude_from") latitudeFrom: Float,
        @Query("longitude_from") longitudeFrom: Float,
        @Query("latitude_to") latitudeTo: Float,
        @Query("longitude_to") longitudeTo: Float,
        @Query("range_from") rangeFrom: Int?,
        @Query("range_to") rangeTo: Int?,
        @Query("timestamp_min") timestampMin: Int?,
        @Query("timestamp_max") timestampMax: Int?,
        @Query("seats_min") seatsMin: Int?,
        @Query("seats_max") seatsMax: Int?,
        @Query("bags_min") bagsMin: Int?,
        @Query("bags_max") bagsMax: Int?,
        @Query("price_min") priceMin: Float?,
        @Query("price_max") priceMax: Float?,
        @Query("pet") pet: Boolean?
    ): Response<MutableList<TripResponse>>
}
