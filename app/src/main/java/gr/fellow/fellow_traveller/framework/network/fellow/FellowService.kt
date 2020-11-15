package gr.fellow.fellow_traveller.framework.network.fellow

import gr.fellow.fellow_traveller.data.BaseResponse
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
import retrofit2.Response
import retrofit2.http.*

interface FellowService {

    /**  AUTH  **/

    @POST("auth/check_email")
    suspend fun checkIfAccountInfoExist(
        @Body emailRequest: CheckEmailRequest
    ): Response<BaseResponse<String>>


    @POST("auth/signup")
    suspend fun registerUser(
        @Body request: AccountCreateRequest
    ): Response<BaseResponse<String>>

    @GET("auth/verify_account")
    suspend fun verifyAccount(
        @Query("token") token: String
    ): Response<BaseResponse<String>>


    @POST("auth/signin")
    suspend fun userLogin(
        @Body user: LoginRequest
    ): Response<BaseResponse<AuthenticationResponse>>


    @POST("auth/logout")
    suspend fun logout(
        @Body logoutRequest: LogoutRequest
    ): Response<BaseResponse<String>>


    @POST("auth/forgot_password")
    suspend fun forgotPassword(
        @Body forgotPasswordRequest: ForgotPasswordRequest
    ): Response<BaseResponse<String>>


    @POST("auth/reset_password")
    suspend fun resetPassword(
        @Body resetPasswordRequest: ResetPasswordRequest
    ): Response<BaseResponse<String>>


    /** User **/

    @PUT("user")
    suspend fun updateAccount(
        @Body updateAccountRequest: UpdateAccountRequest
    ): Response<BaseResponse<UserAuthResponse>>

    @GET("user/me")
    suspend fun getUserInfo(
    ): Response<BaseResponse<UserAuthResponse>>

    @GET("user/{user_id}")
    suspend fun getUserInfo(
        @Path("user_id") userId: String
    ): Response<BaseResponse<UserInfoResponse>>


    @PUT("user/me/picture")
    suspend fun updateUserPicture(
        @Body updatePictureRequest: UpdatePictureRequest
    ): Response<BaseResponse<UserAuthResponse>>


    @PUT("user/me/password")
    suspend fun updateUserPassword(
        @Body updatePasswordRequest: UpdatePasswordRequest
    ): Response<BaseResponse<String>>

    /** CAR **/

    @GET("car")
    suspend fun userCars(): Response<BaseResponse<MutableList<CarInfoResponse>>>


    @POST("car")
    suspend fun addCar(
        @Body carRequest: CarRequest
    ): Response<BaseResponse<CarInfoResponse>>


    @DELETE("car/{car_id}")
    suspend fun deleteCar(
        @Path("car_id") carId: String
    ): Response<BaseResponse<String>>


    /** TRIP **/

    @POST("trip")
    suspend fun registerTrip(
        @Body trip: TripCreateRequest
    ): Response<BaseResponse<TripInvolvedResponse>>

    @GET("trip/{trip_id}")
    suspend fun getTripById(
        @Path("trip_id") tripId: String
    ): Response<BaseResponse<TripInvolvedResponse>>


    @GET("trip")
    suspend fun getTripsAs(
        @Query("type") type: String,
        @Query("status") status: String
        // @Query("page") page: Int
    ): Response<BaseResponse<MutableList<TripInvolvedResponse>>>

    @GET("trip/search")
    suspend fun searchTrips(
        @Query("latitude_from") latitudeFrom: Float,
        @Query("longitude_from") longitudeFrom: Float,
        @Query("latitude_to") latitudeTo: Float,
        @Query("longitude_to") longitudeTo: Float,
        @Query("range_from") rangeFrom: Int?,
        @Query("range_to") rangeTo: Int?,
        @Query("timestamp_min") timestampMin: Long?,
        @Query("timestamp_max") timestampMax: Long?,
        @Query("seats_min") seatsMin: Int?,
        @Query("seats_max") seatsMax: Int?,
        @Query("price_min") priceMin: Int?,
        @Query("price_max") priceMax: Int?,
        @Query("pet") pet: Boolean?
    ): Response<BaseResponse<MutableList<TripSearchResponse>>>


    @PUT("trip/passenger")
    suspend fun bookTrip(
        @Body request: BookTripRequest
    ): Response<BaseResponse<TripInvolvedResponse>>

    @DELETE("trip/passenger/{trip_id}")
    suspend fun exitFromTrip(
        @Path("trip_id") tripId: String
    ): Response<BaseResponse<String>>


    @DELETE("trip/{trip_id}")
    suspend fun deleteTrip(
        @Path("trip_id") tripId: String
    ): Response<BaseResponse<String>>


    /** REVIEW **/

    @POST("review")
    suspend fun registerReview(
        @Body registerReviewRequest: RegisterReviewRequest
    ): Response<BaseResponse<String>>

    @GET("review/check/{targetId}")
    suspend fun checkReview(
        @Path("targetId") targetId: String
    ): Response<BaseResponse<Boolean>>

    /** NOTIFICATION **/

    @GET("notification")
    suspend fun getNotifications(
        @Query("page") page: Int
    ): Response<BaseResponse<MutableList<NotificationResponse>>>

    @PUT("notification")
    suspend fun setNotificationRead(
        @Body updateNotification: UpdateNotification
    ): Response<BaseResponse<String>>
}
