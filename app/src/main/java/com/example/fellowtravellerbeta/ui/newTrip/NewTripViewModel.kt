package com.example.fellowtravellerbeta.ui.newTrip

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fellowtravellerbeta.data.network.ApiRepository
import com.example.fellowtravellerbeta.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class NewTripViewModel (private val service: ApiRepository) : ViewModel() {

    private val _date = SingleLiveEvent<String>()
    val date: LiveData<String> = _date

    private val _time = SingleLiveEvent<String>()
    val time: LiveData<String> = _time


    fun applyDate(date : String){
        viewModelScope.launch {
            _date.value = date
        }

    }

    fun applyTime(time : String){
        viewModelScope.launch {
            _time.value = time
        }

    }
}