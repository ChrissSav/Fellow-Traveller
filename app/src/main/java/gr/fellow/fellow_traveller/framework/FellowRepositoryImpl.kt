package gr.fellow.fellow_traveller.framework

import android.content.SharedPreferences
import gr.fellow.fellow_traveller.data.FellowRepository
import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.domain.SearchFilters
import gr.fellow.fellow_traveller.framework.network.fellow.FellowService
import gr.fellow.fellow_traveller.framework.network.fellow.request.*
import gr.fellow.fellow_traveller.framework.network.fellow.response.CarResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.StatusHandleResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.TripResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserLoginResponse
import gr.fellow.fellow_traveller.utils.ConnectivityHelper
import gr.fellow.fellow_traveller.utils.PREFS_AUTH_TOKEN
import gr.fellow.fellow_traveller.utils.handleToCorrectFormat
import gr.fellow.fellow_traveller.utils.networkCall
import gr.fellow.fellow_traveller.utils.set

class FellowRepositoryImpl(
    private val service: FellowService,
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
        networkCall(connectivityHelper) {
            val res = service.userLogin(loginRequest)
            if (res.isSuccessful)
                sharedPrefs[PREFS_AUTH_TOKEN] =
                    res.headers()["Set-Cookie"]?.split(";".toRegex())?.toTypedArray()?.get(0)
            res.handleToCorrectFormat()
        }


    override suspend fun addCarRemote(carRequest: CarRequest): ResultWrapper<CarResponse> =
        networkCall(connectivityHelper) {
            service.addCar(carRequest).handleToCorrectFormat()
        }

    override suspend fun getCarsRemote(): ResultWrapper<ArrayList<CarResponse>> =
        networkCall(connectivityHelper) {
            service.userCars().handleToCorrectFormat()
        }

    override suspend fun deleteCarRemote(carId: Int): ResultWrapper<StatusHandleResponse> =
        networkCall(connectivityHelper) {
            service.deleteCar(carId).handleToCorrectFormat()
        }

    override suspend fun addTrip(trip: TripCreateRequest): ResultWrapper<TripResponse> =
        networkCall(connectivityHelper) {
            service.addTrip(trip).handleToCorrectFormat()
        }

    override suspend fun getTipsAsCreator(): ResultWrapper<MutableList<TripResponse>> =
        networkCall(connectivityHelper) {
            service.getTripsAs("creator").handleToCorrectFormat()
        }

    override suspend fun getTipsAsPassenger(): ResultWrapper<MutableList<TripResponse>> =
        networkCall(connectivityHelper) {
            service.getTripsAs("passenger").handleToCorrectFormat()
        }

    override suspend fun searchTrips(query: SearchFilters): ResultWrapper<MutableList<TripResponse>> =
        networkCall(connectivityHelper) {
            with(query) {
                service.searchTrips(
                    latitudeFrom, longitudeFrom, latitudeTo, longitudeTo, rangeFrom,
                    rangeTo, timestampMin, timestampMax, seatsMin, seatsMax,
                    bagsMin, bagsMax, priceMin, priceMax, pet
                ).handleToCorrectFormat()
            }

        }


}