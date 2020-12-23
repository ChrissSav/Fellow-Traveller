package gr.fellow.fellow_traveller.domain

import android.net.Uri
import gr.fellow.fellow_traveller.domain.car.Car
import gr.fellow.fellow_traveller.domain.notification.Notification
import gr.fellow.fellow_traveller.domain.review.Review
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.domain.trip.TripSearch
import gr.fellow.fellow_traveller.domain.user.LocalUser
import gr.fellow.fellow_traveller.domain.user.UserInfo
import gr.fellow.fellow_traveller.framework.network.fellow.car.CarRequest
import gr.fellow.fellow_traveller.framework.network.fellow.notification.UpdateNotification
import gr.fellow.fellow_traveller.framework.network.fellow.review.RegisterReviewRequest
import gr.fellow.fellow_traveller.framework.network.fellow.user.UpdatePasswordRequest
import gr.fellow.fellow_traveller.framework.network.fellow.user.UserAuthResponse
import gr.fellow.fellow_traveller.framework.network.google.response.DetailsResponse
import gr.fellow.fellow_traveller.framework.network.google.response.PlaceApiResponse
import retrofit2.Response

interface FellowDataSource {

    //Auth

    suspend fun checkUserEmail(email: String): String

    suspend fun registerUser(firstName: String, lastName: String, email: String, password: String): String

    suspend fun verifyAccount(token: String): UserAuthResponse

    suspend fun loginUser(username: String, password: String): UserAuthResponse

    suspend fun logoutRemote(): String

    suspend fun registerUserAuth(userAuthResponse: UserAuthResponse)

    suspend fun registerUserAuth(userLocal: LocalUser)

    suspend fun forgotPassword(email: String): String

    suspend fun resetPassword(email: String, code: String, password: String): String

    // User

    suspend fun updateAccount(firstName: String, lastName: String, aboutMe: String?): UserAuthResponse

    suspend fun updatePicture(picture: String?): UserAuthResponse

    suspend fun updateUserMessenger(messenger : String): UserAuthResponse

    suspend fun getUserInfoRemote(): UserAuthResponse

    suspend fun getUserInfoById(userId: String): UserInfo

    suspend fun changePassword(updatePasswordRequest: UpdatePasswordRequest): String

    // Cars

    suspend fun getCarsRemote(): MutableList<Car>

    suspend fun addCarRemote(carRequest: CarRequest): Car

    suspend fun deleteCarRemote(carId: String): String


    // Trips


    suspend fun addTripRemote(
        destFrom: String, destTo: String, carId: String,
        hasPet: Boolean, seats: Int, bags: Int, msg: String?, price: Float, timestamp: Long
    ): TripInvolved

    suspend fun searchTrips(query: SearchTripFilter): MutableList<TripSearch>

    suspend fun bookTrip(tripId: String, seats: Int, pet: Boolean): TripInvolved

    suspend fun getTipsAsCreator(status: String): MutableList<TripInvolved>

    suspend fun getTipsAsPassenger(status: String): MutableList<TripInvolved>

    suspend fun getTripInvolvedById(tripId: String): TripInvolved

    suspend fun exitFromTrip(tripId: String): String

    suspend fun deleteTrip(tripId: String): String


    // Notification

    suspend fun getNotification(): MutableList<Notification>

    suspend fun getNotificationSocket(): MutableList<Notification>

    suspend fun setNotificationRead(updateNotification: UpdateNotification): String


    // Review

    suspend fun registerReview(registerReviewRequest: RegisterReviewRequest): String

    suspend fun checkReview(targetId: String): Boolean

    suspend fun getUserReviews(targetId: String): MutableList<Review>


    /**
     * Firebase
     */


    suspend fun updatePictureFirebase(uri: Uri, userId: String): String

    suspend fun sendFirebaseMessage(hashMap: HashMap<String, Any>)

    /**
     * Google Service
     */

    suspend fun getPlaces(place: String): Response<PlaceApiResponse>

    suspend fun getPlacesLanLon(placeId: String): Response<DetailsResponse>

    /**
     * local DB
     */

    suspend fun loadUsersInfoLocal(): LocalUser

    suspend fun logoutUserLocal(): Int


    //  suspend fun addTripLocal(tripCreateRequest: TripCreateRequest): TripResponse>

}