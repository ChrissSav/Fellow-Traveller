package gr.fellow.fellow_traveller.data

import gr.fellow.fellow_traveller.domain.SearchTripFilter
import gr.fellow.fellow_traveller.framework.network.fellow.auth.*
import gr.fellow.fellow_traveller.framework.network.fellow.car.CarInfoResponse
import gr.fellow.fellow_traveller.framework.network.fellow.car.CarRequest
import gr.fellow.fellow_traveller.framework.network.fellow.notification.NotificationResponse
import gr.fellow.fellow_traveller.framework.network.fellow.notification.UpdateNotification
import gr.fellow.fellow_traveller.framework.network.fellow.review.RegisterReviewRequest
import gr.fellow.fellow_traveller.framework.network.fellow.trip.BookTripRequest
import gr.fellow.fellow_traveller.framework.network.fellow.trip.TripCreateRequest
import gr.fellow.fellow_traveller.framework.network.fellow.trip.TripInvolvedResponse
import gr.fellow.fellow_traveller.framework.network.fellow.trip.TripSearchResponse
import gr.fellow.fellow_traveller.framework.network.fellow.user.*
import gr.fellow.fellow_traveller.room.entites.CarEntity
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity

interface FellowRepository {

    // Auth

    suspend fun checkField(checkEmailRequest: CheckEmailRequest): ResultWrapper<String>

    suspend fun registerUserRemote(registerUserRequest: AccountCreateRequest): ResultWrapper<String>

    suspend fun verifyAccount(token: String): ResultWrapper<String>

    suspend fun loginUserRemote(loginRequest: LoginRequest): ResultWrapper<UserAuthResponse>

    suspend fun logout(): ResultWrapper<String>

    suspend fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest): ResultWrapper<String>

    suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest): ResultWrapper<String>

    // User

    suspend fun updateAccountInfo(updateAccountRequest: UpdateAccountRequest): ResultWrapper<UserAuthResponse>

    suspend fun updateUserPicture(updatePictureRequest: UpdatePictureRequest): ResultWrapper<UserAuthResponse>

    suspend fun getUserInfo(): ResultWrapper<UserAuthResponse>

    suspend fun getUserInfoById(userId: String): ResultWrapper<UserInfoResponse>

    suspend fun changePassword(updatePasswordRequest: UpdatePasswordRequest): ResultWrapper<String>


    // Cars

    suspend fun addCarRemote(carRequest: CarRequest): ResultWrapper<CarInfoResponse>

    suspend fun getCarsRemote(): ResultWrapper<MutableList<CarInfoResponse>>

    suspend fun deleteCarRemote(carId: String): ResultWrapper<String>

    // Trips

    suspend fun registerTripRemote(trip: TripCreateRequest): ResultWrapper<TripInvolvedResponse>

    suspend fun getTipsAsCreator(status: String, page: Int): ResultWrapper<MutableList<TripInvolvedResponse>>

    suspend fun getTipsAsPassenger(status: String, page: Int): ResultWrapper<MutableList<TripInvolvedResponse>>

    suspend fun searchTrips(query: SearchTripFilter): ResultWrapper<MutableList<TripSearchResponse>>

    suspend fun bookTrip(request: BookTripRequest): ResultWrapper<TripInvolvedResponse>

    suspend fun exitFromTrip(tripId: String): ResultWrapper<String>

    suspend fun deleteTrip(tripId: String): ResultWrapper<String>


    // Notification

    suspend fun getNotification(page: Int): ResultWrapper<MutableList<NotificationResponse>>

    suspend fun setNotificationRead(updateNotification: UpdateNotification): ResultWrapper<String>

    // Review

    suspend fun registerReview(registerReviewRequest: RegisterReviewRequest): ResultWrapper<String>

    suspend fun checkReview(targetId: String): ResultWrapper<Boolean>

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