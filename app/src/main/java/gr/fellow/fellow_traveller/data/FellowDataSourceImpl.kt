package gr.fellow.fellow_traveller.data

import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.framework.network.fellow.request.AccountCheckRequest
import gr.fellow.fellow_traveller.framework.network.fellow.request.AccountCreateRequest
import gr.fellow.fellow_traveller.framework.network.fellow.request.LoginRequest
import gr.fellow.fellow_traveller.framework.network.fellow.response.CarResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.StatusHandleResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserInfoResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserLoginResponse
import gr.fellow.fellow_traveller.framework.network.google.PlaceApiRepository
import gr.fellow.fellow_traveller.framework.network.google.response.PlaceApiResponse
import gr.fellow.fellow_traveller.room.entites.CarEntity
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity
import retrofit2.Response

class FellowDataSourceImpl(
    private val repository: FellowRepository,
    private val repositoryLocal: LocalRepository
) : FellowDataSource {

    override suspend fun checkUserPhone(phone: String): ResultWrapper<StatusHandleResponse> =
        repository.checkField(AccountCheckRequest("phone", phone))


    override suspend fun checkUserEmail(email: String): ResultWrapper<StatusHandleResponse> =
        repository.checkField(AccountCheckRequest("email", email))


    override suspend fun registerUser(
        firstName: String, lastName: String, email: String, password: String, phone: String
    ): ResultWrapper<UserLoginResponse> =
        repository.registerUser(AccountCreateRequest(firstName, lastName, email, password, phone))


    override suspend fun loginUser(
        username: String,
        password: String
    ): ResultWrapper<UserLoginResponse> =
        repository.loginUser(LoginRequest(username, password))


    override suspend fun registerUserAuth(userEntity: RegisteredUserEntity) =
        repositoryLocal.registerUserAuth(userEntity)


    override suspend fun getPlaces(place: String): Response<PlaceApiResponse> =
        repository.getPlace(place)

    override suspend fun getCarsRemote(): ResultWrapper<ArrayList<CarResponse>> =
        repository.getCars()


    override suspend fun loadUsersInfo(): RegisteredUserEntity =
        repositoryLocal.loadUserAuth()

    override suspend fun logoutUser() {
        repositoryLocal.logoutUser()
    }

    override suspend fun getAllCars(): MutableList<CarEntity> =
        repositoryLocal.getAllCars()


    override suspend fun insertCar(carEntity: CarEntity) =
        repositoryLocal.insertCar(carEntity)


}