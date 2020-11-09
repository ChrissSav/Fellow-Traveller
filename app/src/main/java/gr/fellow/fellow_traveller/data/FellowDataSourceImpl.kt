package gr.fellow.fellow_traveller.data

import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.domain.SearchTripFilter
import gr.fellow.fellow_traveller.domain.car.Car
import gr.fellow.fellow_traveller.domain.mappers.*
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.domain.trip.TripSearch
import gr.fellow.fellow_traveller.domain.user.LocalUser
import gr.fellow.fellow_traveller.domain.user.UserInfo
import gr.fellow.fellow_traveller.framework.network.fellow.request.*
import gr.fellow.fellow_traveller.framework.network.fellow.response.user.UserAuthResponse
import gr.fellow.fellow_traveller.framework.network.google.response.DetailsResponse
import gr.fellow.fellow_traveller.framework.network.google.response.PlaceApiResponse
import retrofit2.Response

class FellowDataSourceImpl(
    private val repository: FellowRepository,
    private val googleServiceRepository: GoogleServiceRepository
) : FellowDataSource {


    override suspend fun checkUserEmail(email: String): ResultWrapper<String> =
        repository.checkField(CheckEmailRequest(email))

    override suspend fun registerUser(firstName: String, lastName: String, email: String, password: String): ResultWrapper<String> =
        repository.registerUserRemote(AccountCreateRequest(firstName, lastName, email, password))

    override suspend fun verifyAccount(token: String): ResultWrapper<String> =
        repository.verifyAccount(token)

    override suspend fun loginUser(username: String, password: String): ResultWrapper<UserAuthResponse> =
        repository.loginUserRemote(LoginRequest(username, password))

    override suspend fun logoutRemote(): ResultWrapper<String> =
        repository.logout()

    override suspend fun registerUserAuth(userAuthResponse: UserAuthResponse) =
        repository.registerUserAuthLocal(userAuthResponse.mapToRegisteredUserEntity())

    override suspend fun registerUserAuth(userLocal: LocalUser) =
        repository.registerUserAuthLocal(userLocal.mapToRegisteredUserEntity())

    override suspend fun forgotPassword(email: String): ResultWrapper<String> =
        repository.forgotPassword(ForgotPasswordRequest(email))

    override suspend fun resetPassword(email: String, code: String, password: String): ResultWrapper<String> =
        repository.resetPassword(ResetPasswordRequest(email, code, password, password))

    override suspend fun updateAccount(firstName: String, lastName: String, messengerLink: String?, aboutMe: String?): ResultWrapper<UserAuthResponse> =
        repository.updateAccountInfo(UpdateAccountRequest(firstName, lastName, messengerLink, aboutMe))

    override suspend fun updatePicture(picture: String?): ResultWrapper<UserAuthResponse> =
        repository.updateUserPicture(UpdatePictureRequest(picture))

    override suspend fun getUserInfoRemote(): ResultWrapper<UserAuthResponse> =
        repository.getUserInfo()

    override suspend fun getUserInfoById(userId: String): ResultWrapper<UserInfo> {
        return when (val response = repository.getUserInfoById(userId)) {
            is ResultWrapper.Success ->
                ResultWrapper.Success(response.data.mapToUserInfo())
            is ResultWrapper.Error ->
                ResultWrapper.Error(response.error)
        }
    }


    override suspend fun getCarsRemote(): ResultWrapper<MutableList<Car>> {
        return when (val response = repository.getCarsRemote()) {
            is ResultWrapper.Success -> {
                ResultWrapper.Success(response.data.map {
                    it.mapToCar()
                }.toMutableList())
            }
            is ResultWrapper.Error -> {
                ResultWrapper.Error(response.error)
            }
        }
    }


    override suspend fun addCarRemote(carRequest: CarRequest): ResultWrapper<Car> {
        return when (val response = repository.addCarRemote(carRequest)) {
            is ResultWrapper.Success -> {
                ResultWrapper.Success(response.data.mapToCar())
            }
            is ResultWrapper.Error -> {
                ResultWrapper.Error(response.error)
            }
        }
    }


    override suspend fun deleteCarRemote(carId: String): ResultWrapper<String> =
        repository.deleteCarRemote(carId)

    override suspend fun addTripRemote(
        destFrom: String, destTo: String, carId: String,
        hasPet: Boolean, seats: Int, bags: String, msg: String?, price: Float, timestamp: Long
    ): ResultWrapper<TripInvolved> {
        val tripCreateRequest = TripCreateRequest(destFrom, destTo, carId, hasPet, seats, bags, msg, price, timestamp)
        return when (val response = repository.registerTripRemote(tripCreateRequest)) {
            is ResultWrapper.Success ->
                ResultWrapper.Success(response.data.mapTripInvolved())
            is ResultWrapper.Error ->
                ResultWrapper.Error(response.error)
        }
    }


    override suspend fun searchTrips(query: SearchTripFilter): ResultWrapper<MutableList<TripSearch>> {
        return when (val response = repository.searchTrips(query)) {
            is ResultWrapper.Success ->
                ResultWrapper.Success(response.data.map {
                    it.mapTripSearch()
                }.toMutableList())
            is ResultWrapper.Error ->
                ResultWrapper.Error(response.error)
        }

    }

    override suspend fun bookTrip(tripId: String, seats: Int, pet: Boolean): ResultWrapper<TripInvolved> {
        return when (val response = repository.bookTrip(BookTripRequest(tripId, seats, pet))) {
            is ResultWrapper.Success ->
                ResultWrapper.Success(response.data.mapTripInvolved())
            is ResultWrapper.Error ->
                ResultWrapper.Error(response.error)
        }
    }

    override suspend fun getTipsAsCreator(status: String, page: Int): ResultWrapper<MutableList<TripInvolved>> {
        return when (val response = repository.getTipsAsCreator(status, page)) {
            is ResultWrapper.Success ->
                ResultWrapper.Success(response.data.map { it.mapTripInvolved() }.toMutableList())
            is ResultWrapper.Error ->
                ResultWrapper.Error(response.error)
        }
    }

    override suspend fun getTipsAsPassenger(status: String, page: Int): ResultWrapper<MutableList<TripInvolved>> {
        return when (val response = repository.getTipsAsPassenger(status, page)) {
            is ResultWrapper.Success ->
                ResultWrapper.Success(response.data.map { it.mapTripInvolved() }.toMutableList())
            is ResultWrapper.Error ->
                ResultWrapper.Error(response.error)
        }
    }

    override suspend fun exitFromTrip(tripId: String): ResultWrapper<String> =
        repository.exitFromTrip(tripId)

    override suspend fun deleteTrip(tripId: String): ResultWrapper<String> =
        repository.deleteTrip(tripId)


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

    override suspend fun getAllCarsLocal(): MutableList<Car> =
        repository.getAllCarsLocal().map { it.mapToCar() }.toMutableList()

    override suspend fun insertCarLocal(car: Car) =
        repository.insertCarLocal(car.mapToCarEntity())

    override suspend fun deleteCarLocal(carId: String) =
        repository.deleteCarByIdLocal(carId)

    override suspend fun deleteAllLocaleCars() =
        repository.deleteCarsLocal()


}