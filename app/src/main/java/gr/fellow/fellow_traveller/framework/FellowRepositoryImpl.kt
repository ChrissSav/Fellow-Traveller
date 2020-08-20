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
import gr.fellow.fellow_traveller.room.dao.CarDao
import gr.fellow.fellow_traveller.room.dao.UserAuthDao
import gr.fellow.fellow_traveller.room.entites.CarEntity
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity
import gr.fellow.fellow_traveller.utils.*

class FellowRepositoryImpl(
    private val service: FellowService,
    private val sharedPrefs: SharedPreferences,
    private val userAuthDao: UserAuthDao,
    private val carDao: CarDao
) : FellowRepository {


    override suspend fun checkField(accountCheckRequest: AccountCheckRequest): ResultWrapper<StatusHandleResponse> =
        networkCall {
            service.checkIfAccountInfoExist(accountCheckRequest).handleToCorrectFormat()
        }


    override suspend fun registerUserRemote(registerUserRequest: AccountCreateRequest): ResultWrapper<UserLoginResponse> =
        networkCall {
            val res = service.registerUser(registerUserRequest)
            if (res.isSuccessful)
                sharedPrefs[PREFS_AUTH_TOKEN] =
                    res.headers()["Set-Cookie"]?.split(";".toRegex())?.toTypedArray()?.get(0)
            res.handleToCorrectFormat()
        }

    override suspend fun loginUserRemote(loginRequest: LoginRequest): ResultWrapper<UserLoginResponse> =
        networkCall {
            val res = service.userLogin(loginRequest)
            if (res.isSuccessful)
                sharedPrefs[PREFS_AUTH_TOKEN] =
                    res.headers()["Set-Cookie"]?.split(";".toRegex())?.toTypedArray()?.get(0)
            res.handleToCorrectFormat()
        }


    override suspend fun addCarRemote(carRequest: CarRequest): ResultWrapper<CarResponse> =
        networkCall {
            service.addCar(carRequest).handleToCorrectFormat()
        }

    override suspend fun getCarsRemote(): ResultWrapper<ArrayList<CarResponse>> =
        networkCall {
            service.userCars().handleToCorrectFormat()
        }

    override suspend fun deleteCarRemote(carId: Int): ResultWrapper<StatusHandleResponse> =
        networkCall {
            service.deleteCar(carId).handleToCorrectFormat()
        }

    override suspend fun addTrip(trip: TripCreateRequest): ResultWrapper<TripResponse> =
        networkCall {
            service.addTrip(trip).handleToCorrectFormat()
        }

    override suspend fun getTipsAsCreator(): ResultWrapper<MutableList<TripResponse>> =
        networkCall {
            service.getTripsAs("creator").handleToCorrectFormat()
        }

    override suspend fun getTipsAsPassenger(): ResultWrapper<MutableList<TripResponse>> =
        networkCall {
            service.getTripsAs("passenger").handleToCorrectFormat()
        }

    override suspend fun searchTrips(query: SearchFilters): ResultWrapper<MutableList<TripResponse>> =
        networkCall {
            with(query) {
                service.searchTrips(
                    latitudeFrom, longitudeFrom, latitudeTo, longitudeTo, rangeFrom,
                    rangeTo, timestampMin, timestampMax, seatsMin, seatsMax,
                    bagsMin, bagsMax, priceMin, priceMax, pet
                ).handleToCorrectFormat()
            }

        }

    override suspend fun registerUserAuthLocal(userEntity: RegisteredUserEntity) =
        roomCall {
            userAuthDao.insertUser(userEntity)
        }


    override suspend fun loadUserAuthLocal(): RegisteredUserEntity =
        roomCall {
            userAuthDao.getUserRegistered()
        }


    override suspend fun logoutUserLocal() =
        roomCall {
            userAuthDao.deleteUser()
        }

    override suspend fun deleteCarsLocal() =
        roomCall {
            carDao.deleteCars()
        }

    override suspend fun deleteCarByIdLocal(id: Int) =
        roomCall {
            carDao.deleteCarById(id)
        }

    override suspend fun getAllCarsLocal() =
        roomCall {
            carDao.getCars()
        }

    override suspend fun insertCarLocal(car: CarEntity) =
        roomCall {
            carDao.insertUser(car)
        }

}