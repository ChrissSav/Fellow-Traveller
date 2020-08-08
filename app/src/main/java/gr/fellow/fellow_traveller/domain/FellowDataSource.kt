package gr.fellow.fellow_traveller.domain

import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.framework.network.fellow.request.CarRequest
import gr.fellow.fellow_traveller.framework.network.fellow.request.TripCreateRequest
import gr.fellow.fellow_traveller.framework.network.fellow.response.CarResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.StatusHandleResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.TripResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserLoginResponse
import gr.fellow.fellow_traveller.framework.network.google.response.PlaceApiResponse
import gr.fellow.fellow_traveller.room.entites.CarEntity
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity
import retrofit2.Response

interface FellowDataSource {
    suspend fun checkUserPhone(phone: String): ResultWrapper<StatusHandleResponse>

    suspend fun checkUserEmail(email: String): ResultWrapper<StatusHandleResponse>


    suspend fun registerUser(firstName: String, lastName: String, email: String, password: String, phone: String): ResultWrapper<UserLoginResponse>


    suspend fun loginUser(username: String, password: String): ResultWrapper<UserLoginResponse>


    suspend fun registerUserAuth(userEntity: RegisteredUserEntity)


    suspend fun getPlaces(place: String): Response<PlaceApiResponse>


    //Cars

    suspend fun getCarsRemote(): ResultWrapper<ArrayList<CarResponse>>

    suspend fun addCarRemote(carRequest: CarRequest): ResultWrapper<CarResponse>

    suspend fun deleteCarRemote(carId: Int): ResultWrapper<StatusHandleResponse>


    //Trips


    suspend fun addTripRemote(tripCreateRequest: TripCreateRequest): ResultWrapper<TripResponse>

    suspend fun getTipsAsCreator(): ResultWrapper<MutableList<TripResponse>>

    suspend fun getTipsAsPassenger(): ResultWrapper<MutableList<TripResponse>>

    /**
     * local DB
     */

    suspend fun loadUsersInfo(): RegisteredUserEntity


    suspend fun logoutUser()


    suspend fun getAllCarsLocal(): MutableList<CarEntity>


    suspend fun insertCarLocal(carEntity: CarEntity)


    suspend fun deleteCarLocal(carId: Int): Int


    //  suspend fun addTripLocal(tripCreateRequest: TripCreateRequest): ResultWrapper<TripResponse>

}