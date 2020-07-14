package com.example.fellowtravellerbeta.ui.newTrip

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fellowtravellerbeta.data.network.google.PlaceApiRepository
import com.example.fellowtravellerbeta.data.network.google.model.DestinationModel
import com.example.fellowtravellerbeta.data.network.google.response.PredictionResponse
import com.example.fellowtravellerbeta.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class NewTripViewModel(private val service: PlaceApiRepository) : ViewModel() {


    private val _destinations = SingleLiveEvent<MutableList<PredictionResponse>>()
    val destinations: LiveData<MutableList<PredictionResponse>> = _destinations

    private val _destinationFrom = SingleLiveEvent<DestinationModel>()
    val destinationFrom: LiveData<DestinationModel> = _destinationFrom

    private val _destinationTo = SingleLiveEvent<DestinationModel>()
    val destinationTo: LiveData<DestinationModel> = _destinationTo

    private val _date = SingleLiveEvent<String>()
    val date: LiveData<String> = _date

    private val _time = SingleLiveEvent<String>()
    val time: LiveData<String> = _time


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


    fun getAllDestinations(title: String) {
        viewModelScope.launch {
            val result = service.getPlacesFromService(title)
            if (result.isSuccessful) {

                result.body()?.let {

                    val res = result.body()?.predictions
                    if (res!!.isNotEmpty()) {
                        _destinations.value = res
                    }


                }
            }
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
}