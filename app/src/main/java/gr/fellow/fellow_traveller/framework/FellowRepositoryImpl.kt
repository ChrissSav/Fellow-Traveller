package gr.fellow.fellow_traveller.framework

import android.content.SharedPreferences
import android.util.Log
import gr.fellow.fellow_traveller.data.FellowRepository
import gr.fellow.fellow_traveller.domain.SearchTripFilter
import gr.fellow.fellow_traveller.framework.network.fellow.FellowService
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
import gr.fellow.fellow_traveller.room.dao.UserAuthDao
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity
import gr.fellow.fellow_traveller.utils.*

class FellowRepositoryImpl(
    private val service: FellowService,
    private val sharedPrefs: SharedPreferences,
    private val userAuthDao: UserAuthDao
) : FellowRepository {


    override suspend fun checkField(checkEmailRequest: CheckEmailRequest): String =
        networkCall {
            service.checkIfAccountInfoExist(checkEmailRequest).handleApiFormat()
        }


    override suspend fun registerUserRemote(registerUserRequest: AccountCreateRequest): String =
        networkCall {
            service.registerUser(registerUserRequest).handleApiFormat()
        }

    override suspend fun verifyAccount(token: String): UserAuthResponse =
        networkCall {
            val response = service.verifyAccount(token).handleApiFormat()
            sharedPrefs[PREFS_AUTH_ACCESS_TOKEN] = response.authenticationToken
            sharedPrefs[PREFS_AUTH_REFRESH_TOKEN] = response.refreshToken
            response.accountInfo
        }

    override suspend fun loginUserRemote(loginRequest: LoginRequest): UserAuthResponse =
        networkCall {
            val response = service.userLogin(loginRequest).handleApiFormat()
            sharedPrefs[PREFS_AUTH_ACCESS_TOKEN] = response.authenticationToken
            sharedPrefs[PREFS_AUTH_REFRESH_TOKEN] = response.refreshToken
            response.accountInfo
        }

    override suspend fun logout(): String =
        networkCall {
            val refreshToken = sharedPrefs.getString(PREFS_AUTH_REFRESH_TOKEN, "").toString()
            val response = service.logout(LogoutRequest(refreshToken)).handleApiFormat()
            sharedPrefs[PREFS_AUTH_ACCESS_TOKEN] = null
            sharedPrefs[PREFS_AUTH_REFRESH_TOKEN] = null
            response
        }

    override suspend fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest): String =
        networkCall {
            service.forgotPassword(forgotPasswordRequest).handleApiFormat()
        }


    override suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest): String =
        networkCall {
            service.resetPassword(resetPasswordRequest).handleApiFormat()
        }

    override suspend fun updateAccountInfo(updateAccountRequest: UpdateAccountRequest): UserAuthResponse =
        networkCall {
            service.updateAccount(updateAccountRequest).handleApiFormat()
        }

    override suspend fun updateUserPicture(updatePictureRequest: UpdatePictureRequest): UserAuthResponse =
        networkCall {
            service.updateUserPicture(updatePictureRequest).handleApiFormat()
        }

    override suspend fun updateUserMessenger(updateMessengerRequest: UpdateMessengerRequest): UserAuthResponse =
        networkCall {
            service.updateUserMessenger(updateMessengerRequest).handleApiFormat()
        }

    override suspend fun getUserInfo(): UserAuthResponse =
        networkCall {
            service.getUserInfo().handleApiFormat()
        }

    override suspend fun getUserInfoById(userId: String): UserInfoResponse =
        networkCall {
            service.getUserInfo(userId).handleApiFormat()
        }

    override suspend fun changePassword(updatePasswordRequest: UpdatePasswordRequest): String =
        networkCall {
            service.updateUserPassword(updatePasswordRequest).handleApiFormat()
        }


    override suspend fun addCarRemote(carRequest: CarRequest): CarInfoResponse =
        networkCall {
            service.addCar(carRequest).handleApiFormat()
        }

    override suspend fun getCarsRemote(): MutableList<CarInfoResponse> =
        networkCall {
            service.userCars().handleApiFormat()
        }

    override suspend fun deleteCarRemote(carId: String): String =
        networkCall {
            service.deleteCar(carId).handleApiFormat()
        }

    override suspend fun registerTripRemote(trip: TripCreateRequest): TripInvolvedResponse =
        networkCall {
            service.registerTrip(trip).handleApiFormat()
        }

    override suspend fun getTipsAsCreator(status: String): MutableList<TripInvolvedResponse> =
        networkCall {
            service.getTripsAs("creator", status).handleApiFormat()
        }

    override suspend fun getTipsAsPassenger(status: String): MutableList<TripInvolvedResponse> =
        networkCall {
            service.getTripsAs("passenger", status).handleApiFormat()
        }

    override suspend fun getTripInvolvedById(tripId: String): TripInvolvedResponse =
        networkCall {
            service.getTripInvolvedById(tripId).handleApiFormat()
        }

    override suspend fun searchTrips(query: SearchTripFilter): MutableList<TripSearchResponse> =
        networkCall {
            with(query) {
                service.searchTrips(
                    latitudeFrom, longitudeFrom, latitudeTo, longitudeTo, rangeFrom,
                    rangeTo, timestampMin, timestampMax, seatsMin, seatsMax, priceMin, priceMax, pet
                ).handleApiFormat()
            }
        }

    override suspend fun bookTrip(request: BookTripRequest): TripInvolvedResponse =
        networkCall {
            service.bookTrip(request).handleApiFormat()
        }

    override suspend fun exitFromTrip(tripId: String): String =
        networkCall {
            service.exitFromTrip(tripId).handleApiFormat()
        }

    override suspend fun deleteTrip(tripId: String): String =
        networkCall {
            service.deleteTrip(tripId).handleApiFormat()
        }

    override suspend fun getNotification(): MutableList<NotificationResponse> =
        networkCall {
            service.getNotifications().handleApiFormat()
        }

    override suspend fun getNotificationsSocket(): MutableList<NotificationResponse> =
        networkCall {
            service.getNotificationsSocket().handleApiFormat()
        }

    override suspend fun setNotificationRead(updateNotification: UpdateNotification): String =
        networkCall {
            service.setNotificationRead(updateNotification).handleApiFormat()
        }

    override suspend fun registerReview(registerReviewRequest: RegisterReviewRequest): String =
        networkCall {
            service.registerReview(registerReviewRequest).handleApiFormat()
        }

    override suspend fun checkReview(targetId: String): Boolean =
        networkCall {
            service.checkReview(targetId).handleApiFormat()
        }

    override suspend fun getUserReviews(targetId: String): MutableList<ReviewResponse> =
        networkCall {
            service.getUserReviews(targetId).handleApiFormat()
        }


    override suspend fun registerUserAuthLocal(userEntity: RegisteredUserEntity) =
        roomCall {
            userAuthDao.insertUser(userEntity)
        }


    override suspend fun loadUserAuthLocal(): RegisteredUserEntity =
        roomCall {
            userAuthDao.getUserRegistered()
        }


    override suspend fun logoutUserLocal() =
        roomCall {
            Log.i("rpjgpoirjgre", "regoireghreigr")
            userAuthDao.deleteUser()
        }


}