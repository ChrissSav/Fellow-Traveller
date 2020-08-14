package gr.fellow.fellow_traveller.ui.home

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import gr.fellow.fellow_traveller.data.BaseApiException
import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.domain.mappers.toCarEntity
import gr.fellow.fellow_traveller.framework.network.fellow.response.TripResponse
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
    private val context: Context,
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val loadUserInfoUseCase: LoadUserInfoUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getUserCarsRemoteUseCase: GetUserCarsRemoteUseCase,
    private val getUserCarsLocalUseCase: GetUserCarsLocalUseCase,
    private val registerCarLocalUseCase: RegisterCarLocalUseCase,
    private val addCarToRemoteUseCase: AddCarToRemoteUseCase,
    private val deleteCarUseCase: DeleteCarUseCase,
    private val getTripsAsCreatorRemoteUseCase: GetTripsAsCreatorRemoteUseCase,
    private val getTripsAsPassengerRemoteUseCase: GetTripsAsPassengerRemoteUseCase

) : BaseViewModel(context) {


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

    private val _tripsAsCreator = MutableLiveData<MutableList<TripResponse>>()
    val tripsAsCreator: LiveData<MutableList<TripResponse>> = _tripsAsCreator

    private val _tripsTakesPart = MutableLiveData<MutableList<TripResponse>>()
    val tripsTakesPart: LiveData<MutableList<TripResponse>> = _tripsTakesPart

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
            } catch (exception: BaseApiException) {
                _cars.value = getUserCarsLocalUseCase()
                throw  exception
            }

        }
    }

    fun addCar(brand: String, model: String, plate: String, color: String) {
        launch {
            when (val response = addCarToRemoteUseCase(brand, model, plate, color)) {
                is ResultWrapper.Success -> {
                    loadCars()
                    _addCarResult.value = true
                }
                is ResultWrapper.Error -> {
                    _error.value = response.error.msg
                }
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
                    _error.value = response.error.msg
                }

            }
        }
    }

    fun loadTripAsCreator() {

        launch {

            /* if (savedStateHandle.get<MutableList<TripResponse>>(SAVED_STATE_LOCATIONS)?.isNotEmpty() == true) {
                 _tripsAsCreator.value = savedStateHandle.get<MutableList<TripResponse>>(SAVED_STATE_LOCATIONS)
                 return@launch
             }
 */
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
}

