package com.example.fellowtravellerbeta.ui.search_trip

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fellowtravellerbeta.data.network.google.PlaceApiRepository
import com.example.fellowtravellerbeta.data.network.google.model.DestinationModel
import com.example.fellowtravellerbeta.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class SearchTripViewModel(private val service: PlaceApiRepository) : ViewModel() {

    private val _destinationFrom = SingleLiveEvent<DestinationModel>()
    val destinationFrom: LiveData<DestinationModel> = _destinationFrom

    private val _destinationTo = SingleLiveEvent<DestinationModel>()
    val destinationTo: LiveData<DestinationModel> = _destinationTo


    private val _timestampFrom = SingleLiveEvent<Long>()
    val timestampFrom: LiveData<Long> = _timestampFrom


    private val _timestampTo = SingleLiveEvent<Long>()
    val timestampTo: LiveData<Long> = _timestampTo

    private val _priceMin = SingleLiveEvent<Int>()
    val priceMin: LiveData<Int> = _priceMin

    private val _priceMax = SingleLiveEvent<Int>()
    val priceMax: LiveData<Int> = _priceMax


    private val _rangeFrom = SingleLiveEvent<Int>()
    val rangeFrom: LiveData<Int> = _rangeFrom


    private val _rangeTo = SingleLiveEvent<Int>()
    val rangeTo: LiveData<Int> = _rangeTo


    private val _pet = SingleLiveEvent<Boolean>()
    val pet: LiveData<Boolean> = _pet

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

    fun setTimestampFrom(id: String, title: String) {
        viewModelScope.launch {
            _timestampFrom.value = DestinationModel(id, title)

        }
    }

    fun setTimestampTo(id: String, title: String) {
        viewModelScope.launch {
            _timestampTo.value = DestinationModel(id, title)

        }
    }

}
