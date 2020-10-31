package gr.fellow.fellow_traveller.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.data.ResultWrapperSecond
import gr.fellow.fellow_traveller.data.base.BaseViewModel
import gr.fellow.fellow_traveller.data.base.SingleLiveEvent
import gr.fellow.fellow_traveller.domain.car.Car
import gr.fellow.fellow_traveller.domain.externalError
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.domain.user.LocalUser
import gr.fellow.fellow_traveller.usecase.LoadUserLocalInfoUseCase
import gr.fellow.fellow_traveller.usecase.auth.DeleteUserAuthLocalUseCase
import gr.fellow.fellow_traveller.usecase.home.*
import gr.fellow.fellow_traveller.usecase.register.RegisterUserLocalUseCase
import gr.fellow.fellow_traveller.usecase.trips.GetTripsAsCreatorRemoteUseCase
import gr.fellow.fellow_traveller.usecase.trips.GetTripsAsPassengerRemoteUseCase
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
    private val updateUserPictureUseCase: UpdateUserPictureUseCase
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


    private val _successUpdateInfo = SingleLiveEvent<Boolean>()
    val successUpdateInfo: LiveData<Boolean> = _successUpdateInfo

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

    fun loadCars() {
        launchSecond {
            try {
                when (val response = getUserCarsRemoteUseCase()) {
                    is ResultWrapperSecond.Success -> {
                        deleteUserLocalCars()
                        for (item in response.data) {
                            registerCarLocalUseCase(item)
                        }
                        _cars.value = getUserCarsLocalUseCase()
                    }
                    is ResultWrapperSecond.Error -> {
                        errorSecond.value = externalError(response.error)
                    }
                }
            } catch (exception: Exception) {
                _cars.value = getUserCarsLocalUseCase()
                throw  exception
            }

        }
    }

    fun loadCarsLocal() {
        launchSecond {
            _cars.value = getUserCarsLocalUseCase()
        }
    }


    fun deleteCar(car: Car) {
        launch {
            /* when (val response = deleteCarUseCase(car.id)) {
                 is ResultWrapper.Success -> {
                     _cars.value = getUserCarsLocalUseCase()
                     _carDeletedId.value = car
                 }
                 is ResultWrapper.Error -> {
                     error.value = R.string.ERROR_CAR_NOT_BELONG_TO_USER
                 }
             }*/
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


    fun updateAccountInfo(firstName: String, lastName: String, messengerLink: String?, aboutMe: String?) {
        launchSecond(true) {
            when (val response = updateAccountInfoUseCase(firstName, lastName, messengerLink, aboutMe)) {
                is ResultWrapperSecond.Success -> {
                    registerUserLocalUseCase(response.data)
                    _user.value = loadUserLocalInfoUseCase()
                    _successUpdateInfo.value = true
                }
                is ResultWrapperSecond.Error -> {
                    errorSecond.value = externalError(response.error)
                }
            }
        }

    }

    fun updateUserImage(picture: String?){
        launchSecond(true) {
            when (val response = updateUserPictureUseCase(picture)) {
                is ResultWrapperSecond.Success -> {
                    registerUserLocalUseCase(response.data)
                    _user.value = loadUserLocalInfoUseCase()

                }
                is ResultWrapperSecond.Error -> {
                    errorSecond.value = externalError(response.error)
                }
            }
        }
    }
}

