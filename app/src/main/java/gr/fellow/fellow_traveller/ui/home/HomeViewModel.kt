package gr.fellow.fellow_traveller.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.domain.car.Car
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.domain.user.LocalUser
import gr.fellow.fellow_traveller.ui.help.BaseViewModel
import gr.fellow.fellow_traveller.ui.help.SingleLiveEvent
import gr.fellow.fellow_traveller.usecase.LoadUserInfoUseCase
import gr.fellow.fellow_traveller.usecase.auth.LogoutUseCase
import gr.fellow.fellow_traveller.usecase.home.DeleteCarUseCase
import gr.fellow.fellow_traveller.usecase.home.GetUserCarsLocalUseCase
import gr.fellow.fellow_traveller.usecase.home.GetUserCarsRemoteUseCase
import gr.fellow.fellow_traveller.usecase.home.RegisterCarLocalUseCase
import gr.fellow.fellow_traveller.usecase.trips.GetTripsAsCreatorRemoteUseCase
import gr.fellow.fellow_traveller.usecase.trips.GetTripsAsPassengerRemoteUseCase


class HomeViewModel
@ViewModelInject
constructor(
    private val loadUserInfoUseCase: LoadUserInfoUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getUserCarsRemoteUseCase: GetUserCarsRemoteUseCase,
    private val getUserCarsLocalUseCase: GetUserCarsLocalUseCase,
    private val registerCarLocalUseCase: RegisterCarLocalUseCase,
    private val deleteCarUseCase: DeleteCarUseCase,
    private val getTripsAsCreatorRemoteUseCase: GetTripsAsCreatorRemoteUseCase,
    private val getTripsAsPassengerRemoteUseCase: GetTripsAsPassengerRemoteUseCase
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

    private val _tripsTakesPart = MutableLiveData<MutableList<TripInvolved>>()
    val tripsTakesPart: LiveData<MutableList<TripInvolved>> = _tripsTakesPart

    /*****************************************************************************************************/

    fun loadUserInfo() {
        launch {
            _user.value = loadUserInfoUseCase()
        }
    }

    fun logOut() {
        launch {
            logoutUseCase()
            _logout.value = true
        }
    }

    fun loadCars() {
        launch {
            try {
                when (val response = getUserCarsRemoteUseCase()) {
                    is ResultWrapper.Success -> {
                        for (item in response.data) {
                            registerCarLocalUseCase(item)
                        }
                        _cars.value = getUserCarsLocalUseCase()
                    }
                    is ResultWrapper.Error -> {
                        error.value = response.error.msg
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
        launch {
            when (val response = deleteCarUseCase(car.id)) {
                is ResultWrapper.Success -> {
                    _cars.value = getUserCarsLocalUseCase()
                    _carDeletedId.value = car
                }
                is ResultWrapper.Error -> {
                    error.value = R.string.ERROR_CAR_NOT_BELONG_TO_USER
                }
            }
        }
    }

    fun loadTripAsCreator() {

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
                    error.value = response.error.msg
                }
            }

        }
    }

    fun loadTripTakesPart() {

        launch {

            if (_tripsTakesPart.value != null) {
                return@launch
            }
            when (val response = getTripsAsPassengerRemoteUseCase()) {
                is ResultWrapper.Success -> {
                    // savedStateHandle.set(SAVED_STATE_LOCATIONS, response.data)
                    _tripsTakesPart.value = response.data.sortedWith(compareBy { it.timestamp }).toMutableList()
                }
                is ResultWrapper.Error -> {
                    error.value = response.error.msg
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
            }

        }
    }

    fun addTripPassenger(trip: TripInvolved) {
        launch {
            tripsTakesPart.value?.let {
                val tempTrip = it
                tempTrip.add(trip)
                _tripsTakesPart.value = tempTrip
            }
        }
    }
}

