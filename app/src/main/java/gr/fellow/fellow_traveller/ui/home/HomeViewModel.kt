package gr.fellow.fellow_traveller.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.data.models.Trip
import gr.fellow.fellow_traveller.domain.mappers.toCarEntity
import gr.fellow.fellow_traveller.room.entites.CarEntity
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity
import gr.fellow.fellow_traveller.ui.help.BaseViewModel
import gr.fellow.fellow_traveller.ui.help.SingleLiveEvent
import gr.fellow.fellow_traveller.usecase.LoadUserInfoUseCase
import gr.fellow.fellow_traveller.usecase.LogoutUseCase
import gr.fellow.fellow_traveller.usecase.home.*
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
    private val addCarToRemoteUseCase: AddCarToRemoteUseCase,
    private val deleteCarUseCase: DeleteCarUseCase,
    private val getTripsAsCreatorRemoteUseCase: GetTripsAsCreatorRemoteUseCase,
    private val getTripsAsPassengerRemoteUseCase: GetTripsAsPassengerRemoteUseCase
) : BaseViewModel() {


    private val _user = MutableLiveData<RegisteredUserEntity>()
    val user: LiveData<RegisteredUserEntity> = _user

    private val _logout = SingleLiveEvent<Boolean>()
    val logout: LiveData<Boolean> = _logout

    private val _cars = MutableLiveData<MutableList<CarEntity>>()
    val cars: LiveData<MutableList<CarEntity>> = _cars

    private val _addCarResult = SingleLiveEvent<Boolean>()
    val addCarResult: LiveData<Boolean> = _addCarResult

    private val _carDeletedId = SingleLiveEvent<CarEntity>()
    val carDeletedId: LiveData<CarEntity> = _carDeletedId

    private val _tripsAsCreator = MutableLiveData<MutableList<Trip>>()
    val tripsAsCreator: LiveData<MutableList<Trip>> = _tripsAsCreator

    private val _tripsTakesPart = MutableLiveData<MutableList<Trip>>()
    val tripsTakesPart: LiveData<MutableList<Trip>> = _tripsTakesPart

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
                            registerCarLocalUseCase(item.toCarEntity())
                        }
                        _cars.value = getUserCarsLocalUseCase()
                    }
                    is ResultWrapper.Error -> {
                        _error.value = response.error.msg
                    }
                }
            } catch (exception: Exception) {
                _cars.value = getUserCarsLocalUseCase()
                throw  exception
            }

        }
    }


    fun deleteCar(car: CarEntity) {
        launch {
            when (val response = deleteCarUseCase(car.id)) {
                is ResultWrapper.Success -> {
                    _carDeletedId.value = car
                }
                is ResultWrapper.Error -> {
                    _error.value = R.string.ERROR_CAR_NOT_BELONG_TO_USER
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
                    _tripsAsCreator.value = response.data
                }
                is ResultWrapper.Error -> {
                    _error.value = response.error.msg
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
                    _tripsTakesPart.value = response.data
                }
                is ResultWrapper.Error -> {
                    _error.value = response.error.msg
                }
            }
        }
    }


    fun addTripCreate(trip: Trip) {
        launch {
            tripsAsCreator.value?.let {
                val tempTrip = it
                tempTrip.add(trip)
                _tripsAsCreator.value = tempTrip
            }

        }
    }

    fun addTripPassenger(trip: Trip) {
        launch {
            tripsTakesPart.value?.let {
                val tempTrip = it
                tempTrip.add(trip)
                _tripsTakesPart.value = tempTrip
            }

        }
    }
}

