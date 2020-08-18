package gr.fellow.fellow_traveller.data

import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.domain.SearchFilters
import gr.fellow.fellow_traveller.framework.network.fellow.request.*
import gr.fellow.fellow_traveller.framework.network.fellow.response.CarResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.StatusHandleResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.TripResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserLoginResponse
import gr.fellow.fellow_traveller.framework.network.google.response.DetailsResponse
import gr.fellow.fellow_traveller.framework.network.google.response.PlaceApiResponse
import gr.fellow.fellow_traveller.room.entites.CarEntity
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity
import retrofit2.Response

class FellowDataSourceImpl(
    private val repository: FellowRepository,
    private val googleServiceRepository: GoogleServiceRepository
) : FellowDataSource {

    override suspend fun checkUserPhone(phone: String): ResultWrapper<StatusHandleResponse> =
        repository.checkField(AccountCheckRequest("phone", phone))

    override suspend fun checkUserEmail(email: String): ResultWrapper<StatusHandleResponse> =
        repository.checkField(AccountCheckRequest("email", email))

    override suspend fun registerUser(
        firstName: String, lastName: String, email: String, password: String, phone: String
    ): ResultWrapper<UserLoginResponse> =
        repository.registerUserRemote(AccountCreateRequest(firstName, lastName, email, password, phone))


    override suspend fun loginUser(username: String, password: String): ResultWrapper<UserLoginResponse> =
        repository.loginUserRemote(LoginRequest(username, password))

    override suspend fun registerUserAuth(userEntity: RegisteredUserEntity) =
        repository.registerUserAuthLocal(userEntity)

    override suspend fun getCarsRemote(): ResultWrapper<ArrayList<CarResponse>> =
        repository.getCarsRemote()

    override suspend fun addCarRemote(carRequest: CarRequest): ResultWrapper<CarResponse> =
        repository.addCarRemote(carRequest)

    override suspend fun deleteCarRemote(carId: Int): ResultWrapper<StatusHandleResponse> =
        repository.deleteCarRemote(carId)

    override suspend fun addTripRemote(tripCreateRequest: TripCreateRequest): ResultWrapper<TripResponse> =
        repository.addTrip(tripCreateRequest)

    override suspend fun getTipsAsCreator(): ResultWrapper<MutableList<TripResponse>> =
        repository.getTipsAsCreator()

    override suspend fun getTipsAsPassenger(): ResultWrapper<MutableList<TripResponse>> =
        repository.getTipsAsPassenger()

    override suspend fun searchTrips(query: SearchFilters): ResultWrapper<MutableList<TripResponse>> =
        repository.searchTrips(query)

    /**
     * Google Service
     * */

    override suspend fun getPlaces(place: String): Response<PlaceApiResponse> =
        googleServiceRepository.getPlaces(place)

    override suspend fun getPlacesLanLon(placeId: String): Response<DetailsResponse> =
        googleServiceRepository.getPlacesLanLon(placeId)

    /**
     * local DB
     */


    override suspend fun loadUsersInfoLocal(): RegisteredUserEntity =
        repository.loadUserAuthLocal()

    override suspend fun logoutUserLocal() =
        repository.logoutUserLocal()

    override suspend fun getAllCarsLocal(): MutableList<CarEntity> =
        repository.getAllCarsLocal()

    override suspend fun insertCarLocal(carEntity: CarEntity) =
        repository.insertCarLocal(carEntity)

    override suspend fun deleteCarLocal(carId: Int) =
        repository.deleteCarByIdLocal(carId)


}