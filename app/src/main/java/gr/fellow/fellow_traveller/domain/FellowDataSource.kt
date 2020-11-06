package gr.fellow.fellow_traveller.domain

import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.domain.car.Car
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.domain.trip.TripSearch
import gr.fellow.fellow_traveller.domain.user.LocalUser
import gr.fellow.fellow_traveller.domain.user.UserInfo
import gr.fellow.fellow_traveller.framework.network.fellow.request.CarRequest
import gr.fellow.fellow_traveller.framework.network.fellow.response.user.UserAuthResponse
import gr.fellow.fellow_traveller.framework.network.google.response.DetailsResponse
import gr.fellow.fellow_traveller.framework.network.google.response.PlaceApiResponse
import retrofit2.Response

interface FellowDataSource {

    //Auth

    suspend fun checkUserEmail(email: String): ResultWrapper<String>

    suspend fun registerUser(firstName: String, lastName: String, email: String, password: String): ResultWrapper<String>

    suspend fun verifyAccount(token: String): ResultWrapper<String>

    suspend fun loginUser(username: String, password: String): ResultWrapper<UserAuthResponse>

    suspend fun logoutRemote(): ResultWrapper<String>

    suspend fun registerUserAuth(userAuthResponse: UserAuthResponse)

    suspend fun registerUserAuth(userLocal: LocalUser)

    suspend fun forgotPassword(email: String): ResultWrapper<String>

    suspend fun resetPassword(email: String, code: String, password: String): ResultWrapper<String>

    // User

    suspend fun updateAccount(firstName: String, lastName: String, messengerLink: String?, aboutMe: String?): ResultWrapper<UserAuthResponse>

    suspend fun updatePicture(picture: String?): ResultWrapper<UserAuthResponse>

    suspend fun getUserInfoRemote(): ResultWrapper<UserAuthResponse>

    suspend fun getUserInfoById(userId: String): ResultWrapper<UserInfo>


    // Cars

    suspend fun getCarsRemote(): ResultWrapper<MutableList<Car>>

    suspend fun addCarRemote(carRequest: CarRequest): ResultWrapper<Car>

    suspend fun deleteCarRemote(carId: String): ResultWrapper<String>


    // Trips


    suspend fun addTripRemote(
        destFrom: String, destTo: String, carId: String,
        hasPet: Boolean, seats: Int, bags: String, msg: String?, price: Float, timestamp: Long
    ): ResultWrapper<TripInvolved>


    suspend fun searchTrips(query: SearchTripFilter): ResultWrapper<MutableList<TripSearch>>

    suspend fun bookTrip(tripId: String, seats: Int, pet: Boolean): ResultWrapper<TripInvolved>

    suspend fun getTipsAsCreator(): ResultWrapper<MutableList<TripInvolved>>

    suspend fun getTipsAsPassenger(): ResultWrapper<MutableList<TripInvolved>>

    suspend fun exitFromTrip(tripId: String): ResultWrapper<String>

    suspend fun deleteTrip(tripId: String): ResultWrapper<String>


    /**
     * Google Service
     */

    suspend fun getPlaces(place: String): Response<PlaceApiResponse>

    suspend fun getPlacesLanLon(placeId: String): Response<DetailsResponse>

    /**
     * local DB
     */

    suspend fun loadUsersInfoLocal(): LocalUser

    suspend fun logoutUserLocal(): Int

    suspend fun getAllCarsLocal(): MutableList<Car>

    suspend fun insertCarLocal(car: Car)

    suspend fun deleteCarLocal(carId: String): Int

    suspend fun deleteAllLocaleCars(): Int

    //  suspend fun addTripLocal(tripCreateRequest: TripCreateRequest): ResultWrapper<TripResponse>

}