package gr.fellow.fellow_traveller.framework

import android.content.SharedPreferences
import gr.fellow.fellow_traveller.framework.network.google.response.PlaceApiResponse
import gr.fellow.fellow_traveller.utils.ConnectivityHelper
import gr.fellow.fellow_traveller.data.FellowRepository
import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.framework.network.fellow.FellowService
import gr.fellow.fellow_traveller.framework.network.fellow.request.AccountCheckRequest
import gr.fellow.fellow_traveller.framework.network.fellow.request.AccountCreateRequest
import gr.fellow.fellow_traveller.framework.network.fellow.request.CarRequest
import gr.fellow.fellow_traveller.framework.network.fellow.request.LoginRequest
import gr.fellow.fellow_traveller.framework.network.fellow.response.CarResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.StatusHandleResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserLoginResponse
import gr.fellow.fellow_traveller.framework.network.google.PlaceApiService
import gr.fellow.fellow_traveller.utils.set
import gr.fellow.fellow_traveller.utils.PREFS_AUTH_TOKEN
import gr.fellow.fellow_traveller.utils.handleToCorrectFormat
import gr.fellow.fellow_traveller.utils.networkCall
import gr.fellow.fellow_traveller.utils.networkCallWithOutWrap
import retrofit2.Response

class FellowRepositoryImpl(
    private val service: FellowService,
    private val servicePlace: PlaceApiService,
    private val connectivityHelper: ConnectivityHelper,
    private val sharedPrefs: SharedPreferences
) : FellowRepository {


    override suspend fun checkField(accountCheckRequest: AccountCheckRequest): ResultWrapper<StatusHandleResponse> =
        networkCall(connectivityHelper) {
            service.checkIfAccountInfoExist(accountCheckRequest).handleToCorrectFormat()
        }


    override suspend fun registerUser(registerUserRequest: AccountCreateRequest): ResultWrapper<UserLoginResponse> =
        networkCall(connectivityHelper) {
            val res = service.registerUser(registerUserRequest)
            if (res.isSuccessful)
                sharedPrefs[PREFS_AUTH_TOKEN] =
                    res.headers()["Set-Cookie"]?.split(";".toRegex())?.toTypedArray()?.get(0)
            res.handleToCorrectFormat()
        }

    override suspend fun loginUser(loginRequest: LoginRequest): ResultWrapper<UserLoginResponse> =
        networkCall(connectivityHelper)
        {
            val res = service.userLogin(loginRequest)
            if (res.isSuccessful)
                sharedPrefs[PREFS_AUTH_TOKEN] =
                    res.headers()["Set-Cookie"]?.split(";".toRegex())?.toTypedArray()?.get(0)
            res.handleToCorrectFormat()
        }

    override suspend fun getPlace(place: String): Response<PlaceApiResponse> =
        networkCallWithOutWrap(
            connectivityHelper
        )
        {
            servicePlace.getPlaces(place)
        }

    override suspend fun addCarRemote(carRequest: CarRequest): ResultWrapper<CarResponse> =
        networkCall {
            service.addCar(carRequest).handleToCorrectFormat()
        }

    override suspend fun getCarsRemote(): ResultWrapper<ArrayList<CarResponse>> =
        networkCall {
            service.userCars().handleToCorrectFormat()
        }


}