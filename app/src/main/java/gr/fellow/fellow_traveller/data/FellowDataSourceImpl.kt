package gr.fellow.fellow_traveller.data

import android.net.Uri
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.domain.SearchTripFilter
import gr.fellow.fellow_traveller.domain.car.Car
import gr.fellow.fellow_traveller.domain.mappers.*
import gr.fellow.fellow_traveller.domain.notification.Notification
import gr.fellow.fellow_traveller.domain.review.Review
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.domain.trip.TripSearch
import gr.fellow.fellow_traveller.domain.user.LocalUser
import gr.fellow.fellow_traveller.domain.user.UserInfo
import gr.fellow.fellow_traveller.framework.network.fellow.auth.*
import gr.fellow.fellow_traveller.framework.network.fellow.car.CarRequest
import gr.fellow.fellow_traveller.framework.network.fellow.notification.UpdateNotification
import gr.fellow.fellow_traveller.framework.network.fellow.review.RegisterReviewRequest
import gr.fellow.fellow_traveller.framework.network.fellow.trip.BookTripRequest
import gr.fellow.fellow_traveller.framework.network.fellow.trip.TripCreateRequest
import gr.fellow.fellow_traveller.framework.network.fellow.user.*
import gr.fellow.fellow_traveller.framework.network.google.response.DetailsResponse
import gr.fellow.fellow_traveller.framework.network.google.response.PlaceApiResponse
import retrofit2.Response
import java.util.*

class FellowDataSourceImpl(
    private val repository: FellowRepository,
    private val googleServiceRepository: GoogleServiceRepository,
    private val firebaseRepository: FirebaseRepository,
) : FellowDataSource {


    override suspend fun checkUserEmail(email: String): String =
        repository.checkField(CheckEmailRequest(email))

    override suspend fun registerUser(firstName: String, lastName: String, email: String, password: String): String =
        repository.registerUserRemote(AccountCreateRequest(firstName, lastName, email, password))

    override suspend fun verifyAccount(token: String): UserAuthResponse =
        repository.verifyAccount(token)

    override suspend fun loginUser(username: String, password: String): UserAuthResponse =
        repository.loginUserRemote(LoginRequest(username, password))

    override suspend fun logoutRemote(): String =
        repository.logout()

    override suspend fun registerUserAuth(userAuthResponse: UserAuthResponse) =
        repository.registerUserAuthLocal(userAuthResponse.mapToRegisteredUserEntity())

    override suspend fun registerUserAuth(userLocal: LocalUser) =
        repository.registerUserAuthLocal(userLocal.mapToRegisteredUserEntity())

    override suspend fun forgotPassword(email: String): String =
        repository.forgotPassword(ForgotPasswordRequest(email))

    override suspend fun resetPassword(email: String, code: String, password: String): String =
        repository.resetPassword(ResetPasswordRequest(email, code, password, password))

    override suspend fun updateAccount(firstName: String, lastName: String, aboutMe: String?): UserAuthResponse =
        repository.updateAccountInfo(UpdateAccountRequest(firstName, lastName, aboutMe))

    override suspend fun updatePicture(picture: String?): UserAuthResponse =
        repository.updateUserPicture(UpdatePictureRequest(picture))

    override suspend fun updateUserMessenger(messenger: String): UserAuthResponse =
        repository.updateUserMessenger(UpdateMessengerRequest(messenger))

    override suspend fun getUserInfoRemote(): UserAuthResponse =
        repository.getUserInfo()

    override suspend fun getUserInfoById(userId: String): UserInfo =
        repository.getUserInfoById(userId).mapToUserInfo()


    override suspend fun changePassword(updatePasswordRequest: UpdatePasswordRequest): String =
        repository.changePassword(updatePasswordRequest)


    override suspend fun getCarsRemote() =
        repository.getCarsRemote().map { it.mapToCar() }.toMutableList()


    override suspend fun addCarRemote(carRequest: CarRequest): Car =
        repository.addCarRemote(carRequest).mapToCar()


    override suspend fun deleteCarRemote(carId: String): String =
        repository.deleteCarRemote(carId)


    override suspend fun getCarColors() =
        repository.getCarColors().map { it.mapToCarColor() }.toMutableList()


    override suspend fun addTripRemote(
        destFrom: String, destTo: String, destPickUp: String, carId: String, hasPet: Boolean, seats: Int, bags: Int, msg: String?, price: Float, timestamp: Long,
    ) = repository.registerTripRemote(TripCreateRequest(destFrom, destTo, destPickUp, carId, hasPet, seats, bags, msg, price, timestamp)).mapTripInvolved()


    override suspend fun searchTrips(query: SearchTripFilter): MutableList<TripSearch> =
        repository.searchTrips(query).map { it.mapTripSearch() }.toMutableList()

    override suspend fun bookTrip(tripId: String, seats: Int, pet: Boolean): TripInvolved =
        repository.bookTrip(BookTripRequest(tripId, seats, pet)).mapTripInvolved()

    override suspend fun getTipsAsCreator(status: String): MutableList<TripInvolved> =
        repository.getTipsAsCreator(status).map { it.mapTripInvolved() }.toMutableList()


    override suspend fun getTipsAsPassenger(status: String): MutableList<TripInvolved> =
        repository.getTipsAsPassenger(status).map { it.mapTripInvolved() }.toMutableList()


    override suspend fun getTipsAsPassengerAndCreator(status: String): MutableList<TripInvolved> =
        repository.getTipsAsPassengerAndCreator(status).map { it.mapTripInvolved() }.toMutableList()

    override suspend fun getTripInvolvedById(tripId: String): TripInvolved =
        repository.getTripInvolvedById(tripId).mapTripInvolved()

    override suspend fun exitFromTrip(tripId: String): String =
        repository.exitFromTrip(tripId)

    override suspend fun getPlaceAutocomplete(query: String, type: String) =
        repository.getPlaceAutocomplete(query, type).map { it.mapDestination() }.toMutableList()

    override suspend fun getPlaceDetails(placeId: String, type: String) =
        repository.getPlaceDetails(placeId, type).mapDestination()

    override suspend fun deleteTrip(tripId: String): String =
        repository.deleteTrip(tripId)

    override suspend fun getNotification(): MutableList<Notification> =
        repository.getNotification().map { it.mapToNotification() }.toMutableList()

    override suspend fun getNotificationSocket(): MutableList<Notification> =
        repository.getNotificationsSocket().map { it.mapToNotification() }.toMutableList()


    override suspend fun setNotificationRead(updateNotification: UpdateNotification): String =
        repository.setNotificationRead(updateNotification)

    override suspend fun registerReview(registerReviewRequest: RegisterReviewRequest): String =
        repository.registerReview(registerReviewRequest)

    override suspend fun checkReview(targetId: String): Boolean =
        repository.checkReview(targetId)

    override suspend fun getUserReviews(targetId: String): MutableList<Review> =
        repository.getUserReviews(targetId).map { it.mapReview() }.toMutableList()

    /**
     * Firebase Service
     **/
    override suspend fun updatePictureFirebase(uri: Uri, userId: String): String =
        firebaseRepository.uploadImage(uri, userId)

    override suspend fun sendFirebaseMessage(hashMap: HashMap<String, Any>, participantsList: MutableList<String>) =
        firebaseRepository.sendMessage(hashMap, participantsList)

    override suspend fun updateSeenStatus(hashMap: HashMap<String, Any>, tripId: String, userId: String) =
        firebaseRepository.updateSeenStatus(hashMap, tripId, userId)

    override suspend fun createOrEnterConversation(myId: String, creatorId: String, tripId: String, tripName: String, picture: String, creatorName: String) =
        firebaseRepository.createOrEnterConversation(myId, creatorId, tripId, tripName, picture, creatorName)

    override suspend fun deleteConversation(userId: String, tripId: String) =
        firebaseRepository.deleteConversation(userId, tripId)

    /**
     * Google Service
     **/

    override suspend fun getPlaces(place: String): Response<PlaceApiResponse> =
        googleServiceRepository.getPlaces(place)

    override suspend fun getPlacesLanLon(placeId: String): Response<DetailsResponse> =
        googleServiceRepository.getPlacesLanLon(placeId)

    /**
     * local DB
     **/


    override suspend fun loadUsersInfoLocal(): LocalUser =
        repository.loadUserAuthLocal().mapToLocalUser()

    override suspend fun logoutUserLocal() =
        repository.logoutUserLocal()

}