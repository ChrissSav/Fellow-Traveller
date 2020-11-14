package gr.fellow.fellow_traveller.framework

import android.content.SharedPreferences
import android.util.Log
import gr.fellow.fellow_traveller.data.FellowRepository
import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.domain.SearchTripFilter
import gr.fellow.fellow_traveller.framework.network.fellow.FellowService
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
import gr.fellow.fellow_traveller.room.dao.CarDao
import gr.fellow.fellow_traveller.room.dao.UserAuthDao
import gr.fellow.fellow_traveller.room.entites.CarEntity
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity
import gr.fellow.fellow_traveller.utils.*

class FellowRepositoryImpl(
    private val service: FellowService,
    private val sharedPrefs: SharedPreferences,
    private val userAuthDao: UserAuthDao,
    private val carDao: CarDao
) : FellowRepository {


    override suspend fun checkField(checkEmailRequest: CheckEmailRequest): ResultWrapper<String> =
        networkCall {
            service.checkIfAccountInfoExist(checkEmailRequest).handleApiFormat()
        }


    override suspend fun registerUserRemote(registerUserRequest: AccountCreateRequest): ResultWrapper<String> =
        networkCall {
            service.registerUser(registerUserRequest).handleApiFormat()
        }

    override suspend fun verifyAccount(token: String): ResultWrapper<String> =
        networkCall {
            service.verifyAccount(token).handleApiFormat()
        }

    override suspend fun loginUserRemote(loginRequest: LoginRequest): ResultWrapper<UserAuthResponse> =
        networkCall {
            when (val response = service.userLogin(loginRequest).handleApiFormat()) {
                is ResultWrapper.Success -> {
                    sharedPrefs[PREFS_AUTH_ACCESS_TOKEN] = response.data.authenticationToken
                    sharedPrefs[PREFS_AUTH_REFRESH_TOKEN] = response.data.refreshToken
                    ResultWrapper.Success(response.data.accountInfo)
                }
                is ResultWrapper.Error -> {
                    ResultWrapper.Error(response.error)
                }
            }
        }

    override suspend fun logout(): ResultWrapper<String> =
        networkCall {
            val refreshToken = sharedPrefs.getString(PREFS_AUTH_REFRESH_TOKEN, "").toString()
            val response = service.logout(LogoutRequest(refreshToken)).handleApiFormat()
            sharedPrefs[PREFS_AUTH_ACCESS_TOKEN] = null
            sharedPrefs[PREFS_AUTH_REFRESH_TOKEN] = null
            response
        }

    override suspend fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest): ResultWrapper<String> =
        networkCall {
            service.forgotPassword(forgotPasswordRequest).handleApiFormat()
        }


    override suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest): ResultWrapper<String> =
        networkCall {
            service.resetPassword(resetPasswordRequest).handleApiFormat()
        }

    override suspend fun updateAccountInfo(updateAccountRequest: UpdateAccountRequest): ResultWrapper<UserAuthResponse> =
        networkCall {
            service.updateAccount(updateAccountRequest).handleApiFormat()
        }

    override suspend fun updateUserPicture(updatePictureRequest: UpdatePictureRequest): ResultWrapper<UserAuthResponse> =
        networkCall {
            service.updateUserPicture(updatePictureRequest).handleApiFormat()
        }

    override suspend fun getUserInfo(): ResultWrapper<UserAuthResponse> =
        networkCall {
            service.getUserInfo().handleApiFormat()
        }

    override suspend fun getUserInfoById(userId: String): ResultWrapper<UserInfoResponse> =
        networkCall {
            service.getUserInfo(userId).handleApiFormat()
        }

    override suspend fun changePassword(updatePasswordRequest: UpdatePasswordRequest): ResultWrapper<String> =
        networkCall {
            service.updateUserPassword(updatePasswordRequest).handleApiFormat()
        }


    override suspend fun addCarRemote(carRequest: CarRequest): ResultWrapper<CarInfoResponse> =
        networkCall {
            service.addCar(carRequest).handleApiFormat()
        }

    override suspend fun getCarsRemote(): ResultWrapper<MutableList<CarInfoResponse>> =
        networkCall {
            service.userCars().handleApiFormat()
        }

    override suspend fun deleteCarRemote(carId: String): ResultWrapper<String> =
        networkCall {
            service.deleteCar(carId).handleApiFormat()
        }

    override suspend fun registerTripRemote(trip: TripCreateRequest): ResultWrapper<TripInvolvedResponse> =
        networkCall {
            service.registerTrip(trip).handleApiFormat()
        }

    override suspend fun getTipsAsCreator(status: String, page: Int): ResultWrapper<MutableList<TripInvolvedResponse>> =
        networkCall {
            service.getTripsAs("creator", status, page).handleApiFormat()
        }

    override suspend fun getTipsAsPassenger(status: String, page: Int): ResultWrapper<MutableList<TripInvolvedResponse>> =
        networkCall {
            service.getTripsAs("passenger", status, page).handleApiFormat()
        }

    override suspend fun getTripById(tripId: String): ResultWrapper<TripInvolvedResponse> =
        networkCall {
            service.getTripById(tripId).handleApiFormat()
        }

    override suspend fun searchTrips(query: SearchTripFilter): ResultWrapper<MutableList<TripSearchResponse>> =
        networkCall {
            with(query) {
                service.searchTrips(
                    latitudeFrom, longitudeFrom, latitudeTo, longitudeTo, rangeFrom,
                    rangeTo, timestampMin, timestampMax, seatsMin, seatsMax, priceMin, priceMax, pet
                ).handleApiFormat()
            }

        }

    override suspend fun bookTrip(request: BookTripRequest): ResultWrapper<TripInvolvedResponse> =
        networkCall {
            service.bookTrip(request).handleApiFormat()
        }

    override suspend fun exitFromTrip(tripId: String): ResultWrapper<String> =
        networkCall {
            service.exitFromTrip(tripId).handleApiFormat()
        }

    override suspend fun deleteTrip(tripId: String): ResultWrapper<String> =
        networkCall {
            service.deleteTrip(tripId).handleApiFormat()
        }

    override suspend fun getNotification(page: Int): ResultWrapper<MutableList<NotificationResponse>> =
        networkCall {
            service.getNotifications(page).handleApiFormat()
        }

    override suspend fun setNotificationRead(updateNotification: UpdateNotification): ResultWrapper<String> =
        networkCall {
            service.setNotificationRead(updateNotification).handleApiFormat()
        }

    override suspend fun registerReview(registerReviewRequest: RegisterReviewRequest): ResultWrapper<String> =
        networkCall {
            service.registerReview(registerReviewRequest).handleApiFormat()
        }

    override suspend fun checkReview(targetId: String): ResultWrapper<Boolean> =
        networkCall {
            service.checkReview(targetId).handleApiFormat()
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

    override suspend fun deleteCarsLocal() =
        roomCall {
            carDao.deleteCars()
        }

    override suspend fun deleteCarByIdLocal(id: String) =
        roomCall {
            carDao.deleteCarById(id)
        }

    override suspend fun getAllCarsLocal() =
        roomCall {
            carDao.getCars()
        }

    override suspend fun insertCarLocal(car: CarEntity) =
        roomCall {
            carDao.insertUser(car)
        }

}