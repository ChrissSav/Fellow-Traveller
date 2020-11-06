package gr.fellow.fellow_traveller.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.data.base.BaseViewModel
import gr.fellow.fellow_traveller.data.base.SingleLiveEvent
import gr.fellow.fellow_traveller.domain.car.Car
import gr.fellow.fellow_traveller.domain.externalError
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.domain.user.LocalUser
import gr.fellow.fellow_traveller.usecase.auth.DeleteUserAuthLocalUseCase
import gr.fellow.fellow_traveller.usecase.home.*
import gr.fellow.fellow_traveller.usecase.register.RegisterUserLocalUseCase
import gr.fellow.fellow_traveller.usecase.trips.DeleteTripUseCase
import gr.fellow.fellow_traveller.usecase.trips.ExitFromTripUseCase
import gr.fellow.fellow_traveller.usecase.trips.GetTripsAsCreatorRemoteUseCase
import gr.fellow.fellow_traveller.usecase.trips.GetTripsAsPassengerRemoteUseCase
import gr.fellow.fellow_traveller.usecase.user.LoadUserLocalInfoUseCase
import kotlinx.coroutines.launch


class HomeViewModel
@ViewModelInject
constructor(
    private val loadUserLocalInfoUseCase: LoadUserLocalInfoUseCase,
    private val deleteUserAuthLocalUseCase: DeleteUserAuthLocalUseCase,
    private val logoutRemoteUseCase: LogoutRemoteUseCase,
    private val getUserCarsRemoteUseCase: GetUserCarsRemoteUseCase,
    private val getUserCarsLocalUseCase: GetUserCarsLocalUseCase,
    private val registerCarLocalUseCase: RegisterCarLocalUseCase,
    private val deleteCarUseCase: DeleteCarUseCase,
    private val updateAccountInfoUseCase: UpdateAccountInfoUseCase,
    private val deleteUserLocalCars: DeleteUserLocalCars,
    private val registerUserLocalUseCase: RegisterUserLocalUseCase,
    private val getTripsAsCreatorRemoteUseCase: GetTripsAsCreatorRemoteUseCase,
    private val getTripsAsPassengerRemoteUseCase: GetTripsAsPassengerRemoteUseCase,
    private val updateUserPictureUseCase: UpdateUserPictureUseCase,
    private val deleteTripUseCase: DeleteTripUseCase,
    private val exitFromTripUseCase: ExitFromTripUseCase
) : BaseViewModel() {


    private val _user = MutableLiveData<LocalUser>()
    val user: LiveData<LocalUser> = _user

    private val _logout = SingleLiveEvent<Boolean>()
    val logout: LiveData<Boolean> = _logout

    private val _cars = MutableLiveData<MutableList<Car>>()
    val cars: LiveData<MutableList<Car>> = _cars

    private val _addCarResult = SingleLiveEvent<Boolean>()
    val addCarResult: LiveData<Boolean> = _addCarResult

    private val _carDeletedId = SingleLiveEvent<Car>()
    val carDeletedId: LiveData<Car> = _carDeletedId

    private val _tripsAsCreator = MutableLiveData<MutableList<TripInvolved>>()
    val tripsAsCreator: LiveData<MutableList<TripInvolved>> = _tripsAsCreator

    private val _tripsAsPassenger = MutableLiveData<MutableList<TripInvolved>>()
    val tripsAsPassenger: LiveData<MutableList<TripInvolved>> = _tripsAsPassenger

    private val _successUpdateInfo = SingleLiveEvent<Boolean>()
    val successUpdateInfo: LiveData<Boolean> = _successUpdateInfo

    private val _successDeletion = SingleLiveEvent<String>()
    val successDeletion: LiveData<String> = _successDeletion

    /*****************************************************************************************************/

    fun loadUserInfo() {
        launch {
            if (_user.value != null) {
                return@launch
            }
            _user.value = loadUserLocalInfoUseCase()
        }
    }

    fun logOut() {
        viewModelScope.launch {
            load.value = true
            try {
                val res = logoutRemoteUseCase()
                val res2 = deleteUserAuthLocalUseCase()
                val res1 = deleteUserLocalCars()
            } catch (e: java.lang.Exception) {

            }
            load.value = false
            _logout.value = true
        }
    }

    fun logOutUnauthorized() {
        viewModelScope.launch {
            try {
                val res2 = deleteUserAuthLocalUseCase()
                val res1 = deleteUserLocalCars()
            } catch (e: java.lang.Exception) {

            }
            _logout.value = true
        }
    }

    fun loadCars() {
        launch {
            try {
                when (val response = getUserCarsRemoteUseCase()) {
                    is ResultWrapper.Success -> {
                        deleteUserLocalCars()
                        for (item in response.data) {
                            registerCarLocalUseCase(item)
                        }
                        _cars.value = getUserCarsLocalUseCase()
                    }
                    is ResultWrapper.Error -> {
                        error.value = externalError(response.error)
                    }
                }
            } catch (exception: Exception) {
                _cars.value = getUserCarsLocalUseCase()
                throw  exception
            }

        }
    }

    fun loadCarsLocal() {
        launch {
            _cars.value = getUserCarsLocalUseCase()
        }
    }


    fun deleteCar(car: Car) {
        launch(true) {
            when (val response = deleteCarUseCase(car.id)) {
                is ResultWrapper.Success -> {
                    _cars.value = getUserCarsLocalUseCase()
                    _carDeletedId.value = car
                }
                is ResultWrapper.Error -> {
                    error.value = externalError(response.error)
                }
            }
        }
    }

    fun loadTripsAsCreator() {

        launch {
            if (_tripsAsCreator.value != null) {
                return@launch
            }
            when (val response = getTripsAsCreatorRemoteUseCase()) {
                is ResultWrapper.Success -> {
                    // savedStateHandle.set(SAVED_STATE_LOCATIONS, response.data)
                    _tripsAsCreator.value = response.data.sortedWith(compareBy { it.timestamp }).toMutableList()
                }
                is ResultWrapper.Error -> {
                    error.value = externalError(response.error)
                }
            }

        }
    }

    fun loadTripsAsPassenger() {

        launch {
            if (_tripsAsPassenger.value != null) {
                return@launch
            }

            when (val response = getTripsAsPassengerRemoteUseCase()) {
                is ResultWrapper.Success -> {
                    //savedStateHandle.set(SAVED_STATE_LOCATIONS, response.data)
                    _tripsAsPassenger.value = response.data.sortedWith(compareBy { it.timestamp }).toMutableList()
                }
                is ResultWrapper.Error -> {
                    error.value = externalError(response.error)
                }
            }
        }
    }


    fun addTripCreate(trip: TripInvolved) {
        launch {
            tripsAsCreator.value?.let {
                val tempTrip = it
                tempTrip.add(trip)
                _tripsAsCreator.value = tempTrip
                increaseUserTrips()
            }

        }
    }

    fun addTripPassenger(trip: TripInvolved) {
        launch {
            _tripsAsPassenger.value?.let {
                val tempTrip = it
                tempTrip.add(trip)
                _tripsAsPassenger.value = tempTrip
                increaseUserTrips(false)
            }
        }
    }


    fun updateAccountInfo(firstName: String, lastName: String, messengerLink: String?, aboutMe: String?) {
        launch(true) {
            when (val response = updateAccountInfoUseCase(firstName, lastName, messengerLink, aboutMe)) {
                is ResultWrapper.Success -> {
                    registerUserLocalUseCase(response.data)
                    _user.value = loadUserLocalInfoUseCase()
                    _successUpdateInfo.value = true
                }
                is ResultWrapper.Error -> {
                    error.value = externalError(response.error)
                }
            }
        }

    }

    fun updateUserImage(picture: String?) {
        launch(true) {
            when (val response = updateUserPictureUseCase(picture)) {
                is ResultWrapper.Success -> {
                    registerUserLocalUseCase(response.data)
                    _user.value = loadUserLocalInfoUseCase()
                    decreaseUserTrips()
                }
                is ResultWrapper.Error -> {
                    error.value = externalError(response.error)
                }
            }
        }
    }

    fun deleteTrip(tripId: String) {
        launch(true) {
            when (val response = deleteTripUseCase(tripId)) {
                is ResultWrapper.Success -> {
                    _tripsAsCreator.value = deleteTripWithId(tripId, _tripsAsCreator.value)
                    decreaseUserTrips()
                    _successDeletion.value = "Επιτυχής διαγραφή ταξιδιού"
                }
                is ResultWrapper.Error -> {
                    error.value = externalError(response.error)
                }
            }
        }
    }


    fun exitFromTrip(tripId: String) {
        launch(true) {
            when (val response = exitFromTripUseCase(tripId)) {
                is ResultWrapper.Success -> {
                    _tripsAsPassenger.value = deleteTripWithId(tripId, _tripsAsPassenger.value)
                    decreaseUserTrips(false)
                    _successDeletion.value = "Επιτυχής αποχώρηση απο το ταξίδι"
                }
                is ResultWrapper.Error -> {
                    error.value = externalError(response.error)
                }
            }
        }
    }

    private suspend fun increaseUserTrips(offers: Boolean = true) {
        if (offers)
            _user.value?.let { user ->
                registerUserLocalUseCase(
                    LocalUser(
                        user.id,
                        user.firstName,
                        user.lastName,
                        user.rate,
                        user.reviews,
                        user.picture,
                        user.aboutMe,
                        user.email,
                        user.messengerLink,
                        user.tripsOffers + 1,
                        user.tripsInvolved

                    )
                )
            }
        else
            _user.value?.let { user ->
                registerUserLocalUseCase(
                    LocalUser(
                        user.id,
                        user.firstName,
                        user.lastName,
                        user.rate,
                        user.reviews,
                        user.picture,
                        user.aboutMe,
                        user.email,
                        user.messengerLink,
                        user.tripsOffers,
                        user.tripsInvolved + 1

                    )
                )
            }
        _user.value = loadUserLocalInfoUseCase()
    }

    private suspend fun decreaseUserTrips(offers: Boolean = true) {
        if (offers)
            _user.value?.let { user ->
                registerUserLocalUseCase(
                    LocalUser(
                        user.id,
                        user.firstName,
                        user.lastName,
                        user.rate,
                        user.reviews,
                        user.picture,
                        user.aboutMe,
                        user.email,
                        user.messengerLink,
                        user.tripsOffers - 1,
                        user.tripsInvolved

                    )
                )
            }
        else
            _user.value?.let { user ->
                registerUserLocalUseCase(
                    LocalUser(
                        user.id,
                        user.firstName,
                        user.lastName,
                        user.rate,
                        user.reviews,
                        user.picture,
                        user.aboutMe,
                        user.email,
                        user.messengerLink,
                        user.tripsOffers,
                        user.tripsInvolved - 1

                    )
                )
            }
        _user.value = loadUserLocalInfoUseCase()
    }


    private fun deleteTripWithId(tripId: String, tripList: MutableList<TripInvolved>?): MutableList<TripInvolved> {
        val tempList = mutableListOf<TripInvolved>()
        if (tripList != null) {
            for (item in tripList) {
                if (item.id != tripId)
                    tempList.add(item)
            }
        }
        return tempList
    }
}

