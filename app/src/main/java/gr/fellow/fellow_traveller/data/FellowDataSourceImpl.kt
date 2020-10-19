package gr.fellow.fellow_traveller.data

import gr.fellow.fellow_traveller.data.models.Trip
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.domain.SearchFilters
import gr.fellow.fellow_traveller.domain.car.Car
import gr.fellow.fellow_traveller.domain.mappers.*
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.domain.user.LocalUser
import gr.fellow.fellow_traveller.framework.network.fellow.request.*
import gr.fellow.fellow_traveller.framework.network.fellow.response.StatusHandleResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserAuthResponse
import gr.fellow.fellow_traveller.framework.network.google.response.DetailsResponse
import gr.fellow.fellow_traveller.framework.network.google.response.PlaceApiResponse
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity
import retrofit2.Response

class FellowDataSourceImpl(
    private val repository: FellowRepository,
    private val googleServiceRepository: GoogleServiceRepository
) : FellowDataSource {


    override suspend fun checkUserEmail(email: String): ResultWrapperSecond<String> =
        repository.checkField(CheckEmailRequest(email))

    override suspend fun registerUser(firstName: String, lastName: String, email: String, password: String): ResultWrapperSecond<String> =
        repository.registerUserRemote(AccountCreateRequest(firstName, lastName, email, password))

    override suspend fun verifyAccount(token: String): ResultWrapperSecond<String> =
        repository.verifyAccount(token)

    override suspend fun loginUser(username: String, password: String): ResultWrapperSecond<UserAuthResponse> =
        repository.loginUserRemote(LoginRequest(username, password))

    override suspend fun logoutRemote(): ResultWrapperSecond<String> =
        repository.logout()

    override suspend fun registerUserAuth(userEntity: RegisteredUserEntity) =
        repository.registerUserAuthLocal(userEntity)

    override suspend fun getCarsRemote(): ResultWrapperSecond<MutableList<Car>> {
        return when (val response = repository.getCarsRemote()) {
            is ResultWrapperSecond.Success -> {
                ResultWrapperSecond.Success(response.data.map {
                    it.mapToCar()
                }.toMutableList())
            }
            is ResultWrapperSecond.Error -> {
                ResultWrapperSecond.Error(response.error)
            }
        }
    }


    override suspend fun addCarRemote(carRequest: CarRequest): ResultWrapperSecond<Car> {
        return when (val response = repository.addCarRemote(carRequest)) {
            is ResultWrapperSecond.Success -> {
                ResultWrapperSecond.Success(response.data.mapToCar())
            }
            is ResultWrapperSecond.Error -> {
                ResultWrapperSecond.Error(response.error)
            }
        }
    }


    override suspend fun deleteCarRemote(carId: Int): ResultWrapper<StatusHandleResponse> =
        repository.deleteCarRemote(carId)

    override suspend fun addTripRemote(tripCreateRequest: TripCreateRequest): ResultWrapper<TripInvolved> {
        return when (val response = repository.addTrip(tripCreateRequest)) {
            is ResultWrapper.Success ->
                ResultWrapper.Success(response.data.mapTripInvolved())
            is ResultWrapper.Error ->
                ResultWrapper.Error(response.error)
        }
    }

    override suspend fun getTipsAsCreator(): ResultWrapper<MutableList<TripInvolved>> {
        return when (val response = repository.getTipsAsCreator()) {
            is ResultWrapper.Success ->
                ResultWrapper.Success(response.data.map { it.mapTripInvolved() }.toMutableList())
            is ResultWrapper.Error ->
                ResultWrapper.Error(response.error)
        }
    }

    override suspend fun getTipsAsPassenger(): ResultWrapper<MutableList<TripInvolved>> {
        return when (val response = repository.getTipsAsPassenger()) {
            is ResultWrapper.Success ->
                ResultWrapper.Success(response.data.map { it.mapTripInvolved() }.toMutableList())
            is ResultWrapper.Error ->
                ResultWrapper.Error(response.error)
        }
    }


    override suspend fun searchTrips(query: SearchFilters): ResultWrapper<MutableList<Trip>> {
        return when (val response = repository.searchTrips(query)) {
            is ResultWrapper.Success ->
                ResultWrapper.Success(response.data.toTrips())
            is ResultWrapper.Error ->
                ResultWrapper.Error(response.error)
        }

    }

    override suspend fun bookTrip(request: BookTripRequest): ResultWrapper<Trip> {
        return when (val response = repository.bookTrip(request)) {
            is ResultWrapper.Success ->
                ResultWrapper.Success(response.data.toTrip())
            is ResultWrapper.Error ->
                ResultWrapper.Error(response.error)
        }
    }

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

    override suspend fun deleteCarLocal(carId: Int) =
        repository.deleteCarByIdLocal(carId)

    override suspend fun deleteAllLocaleCars() =
        repository.deleteCarsLocal()


}