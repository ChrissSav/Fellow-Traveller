package gr.fellow.fellow_traveller.data

import gr.fellow.fellow_traveller.domain.SearchTripFilter
import gr.fellow.fellow_traveller.framework.network.fellow.auth.*
import gr.fellow.fellow_traveller.framework.network.fellow.car.CarInfoResponse
import gr.fellow.fellow_traveller.framework.network.fellow.car.CarRequest
import gr.fellow.fellow_traveller.framework.network.fellow.notification.NotificationResponse
import gr.fellow.fellow_traveller.framework.network.fellow.notification.UpdateNotification
import gr.fellow.fellow_traveller.framework.network.fellow.review.RegisterReviewRequest
import gr.fellow.fellow_traveller.framework.network.fellow.review.ReviewResponse
import gr.fellow.fellow_traveller.framework.network.fellow.trip.BookTripRequest
import gr.fellow.fellow_traveller.framework.network.fellow.trip.TripCreateRequest
import gr.fellow.fellow_traveller.framework.network.fellow.trip.TripInvolvedResponse
import gr.fellow.fellow_traveller.framework.network.fellow.trip.TripSearchResponse
import gr.fellow.fellow_traveller.framework.network.fellow.user.*
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity

interface FellowRepository {

    // Auth

    suspend fun checkField(checkEmailRequest: CheckEmailRequest): String

    suspend fun registerUserRemote(registerUserRequest: AccountCreateRequest): String

    suspend fun verifyAccount(token: String): String

    suspend fun loginUserRemote(loginRequest: LoginRequest): UserAuthResponse

    suspend fun logout(): String

    suspend fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest): String

    suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest): String

    // User

    suspend fun updateAccountInfo(updateAccountRequest: UpdateAccountRequest): UserAuthResponse

    suspend fun updateUserPicture(updatePictureRequest: UpdatePictureRequest): UserAuthResponse

    suspend fun updateUserMessenger(updateMessengerRequest: UpdateMessengerRequest): UserAuthResponse

    suspend fun getUserInfo(): UserAuthResponse

    suspend fun getUserInfoById(userId: String): UserInfoResponse

    suspend fun changePassword(updatePasswordRequest: UpdatePasswordRequest): String



    // Cars

    suspend fun addCarRemote(carRequest: CarRequest): CarInfoResponse

    suspend fun getCarsRemote(): MutableList<CarInfoResponse>

    suspend fun deleteCarRemote(carId: String): String

    // Trips

    suspend fun registerTripRemote(trip: TripCreateRequest): TripInvolvedResponse

    suspend fun getTipsAsCreator(status: String): MutableList<TripInvolvedResponse>

    suspend fun getTipsAsPassenger(status: String): MutableList<TripInvolvedResponse>

    suspend fun getTripById(tripId: String): TripInvolvedResponse

    suspend fun searchTrips(query: SearchTripFilter): MutableList<TripSearchResponse>

    suspend fun bookTrip(request: BookTripRequest): TripInvolvedResponse

    suspend fun exitFromTrip(tripId: String): String

    suspend fun deleteTrip(tripId: String): String


    // Notification

    suspend fun getNotification(page: Int): MutableList<NotificationResponse>

    suspend fun setNotificationRead(updateNotification: UpdateNotification): String

    // Review

    suspend fun registerReview(registerReviewRequest: RegisterReviewRequest): String

    suspend fun checkReview(targetId: String): Boolean

    suspend fun getUserReviews(targetId: String): MutableList<ReviewResponse>


    /**
     * Local
     **/

    suspend fun registerUserAuthLocal(userEntity: RegisteredUserEntity)

    suspend fun loadUserAuthLocal(): RegisteredUserEntity

    suspend fun logoutUserLocal(): Int


}