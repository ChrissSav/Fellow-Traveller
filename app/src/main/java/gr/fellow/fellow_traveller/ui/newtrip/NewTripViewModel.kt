package gr.fellow.fellow_traveller.ui.newtrip

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.fellowtravellerbeta.data.network.google.model.DestinationModel
import com.example.fellowtravellerbeta.data.network.google.response.PredictionResponse
import gr.fellow.fellow_traveller.room.entites.CarEntity
import gr.fellow.fellow_traveller.ui.help.BaseViewModel
import gr.fellow.fellow_traveller.ui.help.SingleLiveEvent
import kotlinx.coroutines.launch

//@ExperimentalCoroutinesApi
class NewTripViewModel
//@ViewModelInject
constructor(
   // private val service: PlaceApiRepository
) : BaseViewModel() {

    private val _destinations = SingleLiveEvent<MutableList<PredictionResponse>>()
    val destinations: LiveData<MutableList<PredictionResponse>> = _destinations

    private val _destinationFrom = SingleLiveEvent<DestinationModel>()
    val destinationFrom: LiveData<DestinationModel> = _destinationFrom

    private val _destinationTo = SingleLiveEvent<DestinationModel>()
    val destinationTo: LiveData<DestinationModel> = _destinationTo

    private val _destinationPickUp = SingleLiveEvent<DestinationModel>()
    val destinationPickUp: LiveData<DestinationModel> = _destinationPickUp

    private val _date = SingleLiveEvent<String>()
    val date: LiveData<String> = _date

    private val _time = SingleLiveEvent<String>()
    val time: LiveData<String> = _time


    private val _car = SingleLiveEvent<CarEntity>()
    val car: LiveData<CarEntity> = _car

    private val _seats = SingleLiveEvent<Int>()
    val seats: LiveData<Int> = _seats

    private val _bags = SingleLiveEvent<Int>()
    val bags: LiveData<Int> = _bags

    private val _pet = SingleLiveEvent<Boolean>()
    val pet: LiveData<Boolean> = _pet

    private val _price = SingleLiveEvent<Float>()
    val price: LiveData<Float> = _price


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

    fun setPrice(price: Float) {
        viewModelScope.launch {
            _price.value = price

        }
    }

    fun  setSeats(seat: Int){
        viewModelScope.launch {
            _seats.value = seat
        }
    }

    fun  setBags(bags: Int){
        viewModelScope.launch {
            _bags.value = bags
        }
    }

    fun  setPet(pet: Boolean){
        viewModelScope.launch {
            _pet.value = pet
        }
    }
}