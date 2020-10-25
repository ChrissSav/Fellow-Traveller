package gr.fellow.fellow_traveller.domain

import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.data.ResultWrapperSecond
import gr.fellow.fellow_traveller.data.models.Trip
import gr.fellow.fellow_traveller.domain.car.Car
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.domain.user.LocalUser
import gr.fellow.fellow_traveller.framework.network.fellow.request.BookTripRequest
import gr.fellow.fellow_traveller.framework.network.fellow.request.CarRequest
import gr.fellow.fellow_traveller.framework.network.fellow.request.TripCreateRequest
import gr.fellow.fellow_traveller.framework.network.fellow.response.StatusHandleResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserAuthResponse
import gr.fellow.fellow_traveller.framework.network.google.response.DetailsResponse
import gr.fellow.fellow_traveller.framework.network.google.response.PlaceApiResponse
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity
import retrofit2.Response

interface FellowDataSource {

    //Auth

    suspend fun checkUserEmail(email: String): ResultWrapperSecond<String>

    suspend fun registerUser(firstName: String, lastName: String, email: String, password: String): ResultWrapperSecond<String>

    suspend fun verifyAccount(token: String): ResultWrapperSecond<String>

    suspend fun loginUser(username: String, password: String): ResultWrapperSecond<UserAuthResponse>

    suspend fun logoutRemote(): ResultWrapperSecond<String>

    suspend fun registerUserAuth(userEntity: RegisteredUserEntity)

    suspend fun forgotPassword(email: String): ResultWrapperSecond<String>

    suspend fun resetPassword(email: String, code: String, password: String): ResultWrapperSecond<String>

    // User

    suspend fun updateAccount(firstName: String, lastName: String, messengerLink: String?, aboutMe: String?): ResultWrapperSecond<UserAuthResponse>

    suspend fun updatePicture(picture: String?): ResultWrapperSecond<UserAuthResponse>

    suspend fun getUserInfoRemote(): ResultWrapperSecond<UserAuthResponse>


    // Cars

    suspend fun getCarsRemote(): ResultWrapperSecond<MutableList<Car>>

    suspend fun addCarRemote(carRequest: CarRequest): ResultWrapperSecond<Car>

    suspend fun deleteCarRemote(carId: Int): ResultWrapper<StatusHandleResponse>


    // Trips


    suspend fun addTripRemote(tripCreateRequest: TripCreateRequest): ResultWrapper<TripInvolved>

    suspend fun getTipsAsCreator(): ResultWrapper<MutableList<TripInvolved>>

    suspend fun getTipsAsPassenger(): ResultWrapper<MutableList<TripInvolved>>

    suspend fun searchTrips(query: SearchFilters): ResultWrapper<MutableList<Trip>>

    suspend fun bookTrip(request: BookTripRequest): ResultWrapper<Trip>


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

    suspend fun deleteCarLocal(carId: Int): Int

    suspend fun deleteAllLocaleCars(): Int

    //  suspend fun addTripLocal(tripCreateRequest: TripCreateRequest): ResultWrapper<TripResponse>

}