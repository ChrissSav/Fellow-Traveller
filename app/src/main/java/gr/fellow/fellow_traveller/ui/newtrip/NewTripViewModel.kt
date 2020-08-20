package gr.fellow.fellow_traveller.ui.newtrip

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import gr.fellow.fellow_traveller.framework.network.google.model.DestinationModel
import gr.fellow.fellow_traveller.framework.network.google.response.PredictionResponse
import gr.fellow.fellow_traveller.room.entites.CarEntity
import gr.fellow.fellow_traveller.ui.help.BaseViewModel
import gr.fellow.fellow_traveller.usecase.home.GetUserCarsLocalUseCase
import kotlinx.coroutines.launch


class NewTripViewModel
@ViewModelInject
constructor(
    private val getUserCarsLocalUseCase: GetUserCarsLocalUseCase
) : BaseViewModel() {

    private val _destinations = MutableLiveData<MutableList<PredictionResponse>>()
    val destinations: LiveData<MutableList<PredictionResponse>> = _destinations

    private val _destinationFrom = MutableLiveData<DestinationModel>()
    val destinationFrom: LiveData<DestinationModel> = _destinationFrom

    private val _destinationTo = MutableLiveData<DestinationModel>()
    val destinationTo: LiveData<DestinationModel> = _destinationTo

    private val _destinationPickUp = MutableLiveData<DestinationModel>()
    val destinationPickUp: LiveData<DestinationModel> = _destinationPickUp

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    private val _time = MutableLiveData<String>()
    val time: LiveData<String> = _time

    private val _car = MutableLiveData<CarEntity>()
    val car: LiveData<CarEntity> = _car

    private val _carList = MutableLiveData<MutableList<CarEntity>>()
    val carList: LiveData<MutableList<CarEntity>> = _carList

    private val _seats = MutableLiveData<Int>()
    val seats: LiveData<Int> = _seats

    private val _bags = MutableLiveData<Int>()
    val bags: LiveData<Int> = _bags

    private val _pet = MutableLiveData<Boolean>()
    val pet: LiveData<Boolean> = _pet

    private val _price = MutableLiveData<Float>()
    val price: LiveData<Float> = _price

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message


    fun applyDate(date: String) {
        viewModelScope.launch {
            _date.value = date
        }

    }

    fun applyTime(time: String) {
        viewModelScope.launch {
            _time.value = time
        }

    }


    fun setDestinationFrom(id: String, title: String) {
        viewModelScope.launch {
            _destinationFrom.value = DestinationModel(id, title)

        }
    }

    fun setDestinationTo(id: String, title: String) {
        viewModelScope.launch {
            _destinationTo.value = DestinationModel(id, title)

        }
    }

    fun setDestinationPickUp(id: String, title: String) {
        viewModelScope.launch {
            _destinationPickUp.value = DestinationModel(id, title)

        }
    }

    fun setPrice(priceCurrent: Float) {
        viewModelScope.launch {
            _price.value = priceCurrent

        }
    }

    fun setSeats(seatCurrent: Int) {
        viewModelScope.launch {
            _seats.value = seatCurrent
        }
    }

    fun setBags(bagsCurrent: Int) {
        viewModelScope.launch {
            _bags.value = bagsCurrent
        }
    }

    fun setPet(pet: Boolean) {
        viewModelScope.launch {
            _pet.value = pet
        }
    }

    fun setMessage(msg: String) {
        _message.value = msg
    }

    fun setCar(carTemp: CarEntity) {
        viewModelScope.launch {
            _car.value = carTemp
        }
    }

    fun loadUserCars() {
        viewModelScope.launch {
            _carList.value = getUserCarsLocalUseCase()
        }
    }


}