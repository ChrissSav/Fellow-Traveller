package gr.fellow.fellow_traveller.framework.network.fellow

import gr.fellow.fellow_traveller.data.BaseResponse
import gr.fellow.fellow_traveller.framework.network.fellow.auth.*
import gr.fellow.fellow_traveller.framework.network.fellow.car.CarColorResponse
import gr.fellow.fellow_traveller.framework.network.fellow.car.CarInfoResponse
import gr.fellow.fellow_traveller.framework.network.fellow.car.CarRequest
import gr.fellow.fellow_traveller.framework.network.fellow.notification.NotificationResponse
import gr.fellow.fellow_traveller.framework.network.fellow.notification.UpdateNotification
import gr.fellow.fellow_traveller.framework.network.fellow.place.PlaceAutocompleteResponse
import gr.fellow.fellow_traveller.framework.network.fellow.place.PlaceDetailsResponse
import gr.fellow.fellow_traveller.framework.network.fellow.review.RegisterReviewRequest
import gr.fellow.fellow_traveller.framework.network.fellow.review.ReviewResponse
import gr.fellow.fellow_traveller.framework.network.fellow.trip.BookTripRequest
import gr.fellow.fellow_traveller.framework.network.fellow.trip.TripCreateRequest
import gr.fellow.fellow_traveller.framework.network.fellow.trip.TripInvolvedResponse
import gr.fellow.fellow_traveller.framework.network.fellow.trip.TripSearchResponse
import gr.fellow.fellow_traveller.framework.network.fellow.user.*
import retrofit2.Response
import retrofit2.http.*

interface FellowService {

    /**  AUTH  **/

    @POST("auth/check-email")
    suspend fun checkIfAccountInfoExist(
        @Body emailRequest: CheckEmailRequest,
    ): Response<BaseResponse<String>>


    @POST("auth/signup")
    suspend fun registerUser(
        @Body request: AccountCreateRequest,
    ): Response<BaseResponse<String>>

    @GET("auth/verify-account/phone")
    suspend fun verifyAccount(
        @Query("token") token: String,
    ): Response<BaseResponse<AuthenticationResponse>>


    @POST("auth/signin")
    suspend fun userLogin(
        @Body user: LoginRequest,
    ): Response<BaseResponse<AuthenticationResponse>>


    @POST("auth/logout")
    suspend fun logout(
        @Body logoutRequest: LogoutRequest,
    ): Response<BaseResponse<String>>


    @POST("auth/forgot-password")
    suspend fun forgotPassword(
        @Body forgotPasswordRequest: ForgotPasswordRequest,
    ): Response<BaseResponse<String>>


    @POST("auth/reset-password")
    suspend fun resetPassword(
        @Body resetPasswordRequest: ResetPasswordRequest,
    ): Response<BaseResponse<String>>


    /** User **/

    @PUT("users")
    suspend fun updateAccount(
        @Body updateAccountRequest: UpdateAccountRequest,
    ): Response<BaseResponse<UserAuthResponse>>

    @GET("users")
    suspend fun getUserInfo(
    ): Response<BaseResponse<UserAuthResponse>>

    @GET("users/{user_id}")
    suspend fun getUserInfo(
        @Path("user_id") userId: String,
    ): Response<BaseResponse<UserInfoResponse>>


    @PUT("users/me/picture")
    suspend fun updateUserPicture(
        @Body updatePictureRequest: UpdatePictureRequest,
    ): Response<BaseResponse<UserAuthResponse>>


    @PUT("users/me/messenger")
    suspend fun updateUserMessenger(
        @Body updateMessengerRequest: UpdateMessengerRequest,
    ): Response<BaseResponse<UserAuthResponse>>


    @PUT("users/me/password")
    suspend fun updateUserPassword(
        @Body updatePasswordRequest: UpdatePasswordRequest,
    ): Response<BaseResponse<String>>

    /** CAR **/

    @GET("cars")
    suspend fun userCars(): Response<BaseResponse<MutableList<CarInfoResponse>>>


    @POST("cars")
    suspend fun addCar(
        @Body carRequest: CarRequest,
    ): Response<BaseResponse<CarInfoResponse>>


    @DELETE("cars/{car_id}")
    suspend fun deleteCar(
        @Path("car_id") carId: String,
    ): Response<BaseResponse<String>>


    @GET("cars/colors")
    suspend fun getCarColors(): Response<BaseResponse<MutableList<CarColorResponse>>>

    /** TRIP **/

    @GET("trips")
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
        @Query("pet") pet: Boolean?,
    ): Response<BaseResponse<MutableList<TripSearchResponse>>>


    @POST("trips")
    suspend fun registerTrip(
        @Body trip: TripCreateRequest,
    ): Response<BaseResponse<TripInvolvedResponse>>

    @GET("trips/involved/{trip_id}")
    suspend fun getTripInvolvedById(
        @Path("trip_id") tripId: String,
    ): Response<BaseResponse<TripInvolvedResponse>>


    @GET("trips/involved")
    suspend fun getTripsAs(
        @Query("type") type: String?,
        @Query("status") status: String,
    ): Response<BaseResponse<MutableList<TripInvolvedResponse>>>


    @PUT("trips/passenger")
    suspend fun bookTrip(
        @Body request: BookTripRequest,
    ): Response<BaseResponse<TripInvolvedResponse>>

    @DELETE("trips/passenger/{trip_id}")
    suspend fun exitFromTrip(
        @Path("trip_id") tripId: String,
    ): Response<BaseResponse<String>>


    @DELETE("trips/{trip_id}")
    suspend fun deleteTrip(
        @Path("trip_id") tripId: String,
    ): Response<BaseResponse<String>>


    /** Place **/
    @GET("place/autocomplete")
    suspend fun getPlaceAutocomplete(
        @Query("query") query: String,
        @Query("type") type: String,
    ): Response<BaseResponse<MutableList<PlaceAutocompleteResponse>>>

    @GET("place/details")
    suspend fun getPlaceDetails(
        @Query("place_id") placeId: String,
        @Query("type") type: String,
    ): Response<BaseResponse<PlaceDetailsResponse>>


    /** REVIEW **/

    @POST("reviews")
    suspend fun registerReview(
        @Body registerReviewRequest: RegisterReviewRequest,
    ): Response<BaseResponse<String>>

    @GET("reviews/check/{targetId}")
    suspend fun checkReview(
        @Path("targetId") targetId: String,
    ): Response<BaseResponse<Boolean>>

    @GET("reviews/{user_id}")
    suspend fun getUserReviews(
        @Path("user_id") userId: String,
    ): Response<BaseResponse<MutableList<ReviewResponse>>>

    /** NOTIFICATION **/

    @GET("notifications")
    suspend fun getNotifications(): Response<BaseResponse<MutableList<NotificationResponse>>>

    @GET("notifications/socket")
    suspend fun getNotificationsSocket(): Response<BaseResponse<MutableList<NotificationResponse>>>

    @PUT("notifications")
    suspend fun setNotificationRead(
        @Body updateNotification: UpdateNotification,
    ): Response<BaseResponse<String>>
}
