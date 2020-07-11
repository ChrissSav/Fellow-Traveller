package com.example.fellowtravellerbeta.ui.register.fragments.phone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fellowtravellerbeta.data.network.ApiRepository
import com.example.fellowtravellerbeta.data.network.FellowTravellerApiService
import kotlinx.coroutines.launch

class SetPhoneViewModel(private val service: ApiRepository) : ViewModel() {


    private val _checkPhone = MutableLiveData<Boolean>()
    val checkPhone: LiveData<Boolean> = _checkPhone


    fun checkUserPhone(phone: String) {

        viewModelScope.launch {

            _checkPhone.value = service.checkUserPhone(phone).isSuccessful

        }


    }


}