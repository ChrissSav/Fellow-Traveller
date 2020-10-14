package gr.fellow.fellow_traveller.data

import gr.fellow.fellow_traveller.domain.SearchFilters
import gr.fellow.fellow_traveller.framework.network.fellow.request.*
import gr.fellow.fellow_traveller.framework.network.fellow.response.CarResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.StatusHandleResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.TripResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserAuthResponse
import gr.fellow.fellow_traveller.room.entites.CarEntity
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity

interface FellowRepository {

    suspend fun checkField(checkEmailRequest: CheckEmailRequest): ResultWrapperSecond<String>

    suspend fun registerUserRemote(registerUserRequest: AccountCreateRequest): ResultWrapperSecond<String>

    suspend fun verifyAccount(token: String): ResultWrapperSecond<String>

    suspend fun loginUserRemote(loginRequest: LoginRequest): ResultWrapperSecond<UserAuthResponse>

    suspend fun logout(): ResultWrapperSecond<String>

    // Cars

    suspend fun addCarRemote(carRequest: CarRequest): ResultWrapper<CarResponse>

    suspend fun getCarsRemote(): ResultWrapper<MutableList<CarResponse>>

    suspend fun deleteCarRemote(carId: Int): ResultWrapper<StatusHandleResponse>

    // Trips

    suspend fun addTrip(trip: TripCreateRequest): ResultWrapper<TripResponse>

    suspend fun getTipsAsCreator(): ResultWrapper<MutableList<TripResponse>>

    suspend fun getTipsAsPassenger(): ResultWrapper<MutableList<TripResponse>>

    suspend fun searchTrips(query: SearchFilters): ResultWrapper<MutableList<TripResponse>>

    suspend fun bookTrip(request: BookTripRequest): ResultWrapper<TripResponse>


    /**
     * Local
     **/

    suspend fun registerUserAuthLocal(userEntity: RegisteredUserEntity)

    suspend fun loadUserAuthLocal(): RegisteredUserEntity

    suspend fun logoutUserLocal()

    // Cars

    suspend fun deleteCarsLocal()

    suspend fun deleteCarByIdLocal(id: Int): Int

    suspend fun getAllCarsLocal(): MutableList<CarEntity>

    suspend fun insertCarLocal(car: CarEntity)

}