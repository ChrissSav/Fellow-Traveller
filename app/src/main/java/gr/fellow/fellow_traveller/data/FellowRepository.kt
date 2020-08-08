package gr.fellow.fellow_traveller.data

import gr.fellow.fellow_traveller.framework.network.fellow.request.*
import gr.fellow.fellow_traveller.framework.network.fellow.response.CarResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.StatusHandleResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.TripResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserLoginResponse
import gr.fellow.fellow_traveller.framework.network.google.response.PlaceApiResponse
import retrofit2.Response

interface FellowRepository {

    suspend fun checkField(accountCheckRequest: AccountCheckRequest): ResultWrapper<StatusHandleResponse>

    suspend fun registerUser(registerUserRequest: AccountCreateRequest): ResultWrapper<UserLoginResponse>

    suspend fun loginUser(loginRequest: LoginRequest): ResultWrapper<UserLoginResponse>

    suspend fun getPlace(place: String): Response<PlaceApiResponse>


    /**
     * Cars
     **/

    suspend fun addCarRemote(carRequest: CarRequest): ResultWrapper<CarResponse>

    suspend fun getCarsRemote(): ResultWrapper<ArrayList<CarResponse>>

    suspend fun deleteCarRemote(carId: Int): ResultWrapper<StatusHandleResponse>


    /**
     * Trips
     * */

    suspend fun addTrip(trip: TripCreateRequest): ResultWrapper<TripResponse>

    suspend fun getTipsAsCreator(): ResultWrapper<MutableList<TripResponse>>

    suspend fun getTipsAsPassenger(): ResultWrapper<MutableList<TripResponse>>
}