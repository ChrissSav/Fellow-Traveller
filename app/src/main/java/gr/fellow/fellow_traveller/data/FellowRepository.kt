package gr.fellow.fellow_traveller.data

import gr.fellow.fellow_traveller.domain.SearchTripFilter
import gr.fellow.fellow_traveller.framework.network.fellow.request.*
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserAuthResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.car.CarInfoResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.trip.TripInvolvedResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.trip.TripSearchResponse
import gr.fellow.fellow_traveller.room.entites.CarEntity
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity

interface FellowRepository {

    // Auth

    suspend fun checkField(checkEmailRequest: CheckEmailRequest): ResultWrapperSecond<String>

    suspend fun registerUserRemote(registerUserRequest: AccountCreateRequest): ResultWrapperSecond<String>

    suspend fun verifyAccount(token: String): ResultWrapperSecond<String>

    suspend fun loginUserRemote(loginRequest: LoginRequest): ResultWrapperSecond<UserAuthResponse>

    suspend fun logout(): ResultWrapperSecond<String>

    suspend fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest): ResultWrapperSecond<String>

    suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest): ResultWrapperSecond<String>

    // User

    suspend fun updateAccountInfo(updateAccountRequest: UpdateAccountRequest): ResultWrapperSecond<UserAuthResponse>

    suspend fun updateUserPicture(updatePictureRequest: UpdatePictureRequest): ResultWrapperSecond<UserAuthResponse>

    suspend fun getUserInfo(): ResultWrapperSecond<UserAuthResponse>


    // Cars

    suspend fun addCarRemote(carRequest: CarRequest): ResultWrapperSecond<CarInfoResponse>

    suspend fun getCarsRemote(): ResultWrapperSecond<MutableList<CarInfoResponse>>

    suspend fun deleteCarRemote(carId: String): ResultWrapperSecond<String>

    // Trips

    suspend fun registerTripRemote(trip: TripCreateRequest): ResultWrapperSecond<TripInvolvedResponse>

    suspend fun getTipsAsCreator(): ResultWrapperSecond<MutableList<TripInvolvedResponse>>

    suspend fun getTipsAsPassenger(): ResultWrapperSecond<MutableList<TripInvolvedResponse>>

    suspend fun searchTrips(query: SearchTripFilter): ResultWrapperSecond<MutableList<TripSearchResponse>>

    suspend fun bookTrip(request: BookTripRequest): ResultWrapperSecond<TripInvolvedResponse>


    /**
     * Local
     **/

    suspend fun registerUserAuthLocal(userEntity: RegisteredUserEntity)

    suspend fun loadUserAuthLocal(): RegisteredUserEntity

    suspend fun logoutUserLocal(): Int

    // Cars

    suspend fun deleteCarsLocal(): Int

    suspend fun deleteCarByIdLocal(id: String): Int

    suspend fun getAllCarsLocal(): MutableList<CarEntity>

    suspend fun insertCarLocal(car: CarEntity)

}