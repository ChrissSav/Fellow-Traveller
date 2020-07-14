package com.example.fellowtravellerbeta.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fellowtravellerbeta.data.network.fellow.ApiRepository
import com.example.fellowtravellerbeta.utils.SingleLiveEvent
import com.example.fellowtravellerbeta.utils.getModelFromResponseErrorBody
import kotlinx.coroutines.launch

class RegisterSharedViewModel(private val service: ApiRepository) : ViewModel() {


    private val _phone = SingleLiveEvent<String>()
    val phone: LiveData<String> = _phone

    private val _email = SingleLiveEvent<String>()
    val email: LiveData<String> = _email


    private val _password = SingleLiveEvent<String>()
    val password: LiveData<String> = _password


    private val _userInfo = SingleLiveEvent<Pair<String, String>>()
    val userInfo: LiveData<Pair<String, String>> = _userInfo


    private val _responseResult = SingleLiveEvent<Int>()
    val responseResult: LiveData<Int> = _responseResult


    fun checkUserPhone(phone: String) {

        viewModelScope.launch {

            val response = service.checkUserPhone(phone)
            if (response.isSuccessful) {
                _phone.value = phone
            } else {
                _phone.value = null
            }

        }


    }


    fun checkUserEmail(email: String) {

        viewModelScope.launch {

            val response = service.checkUserEmail(email)
            if (response.isSuccessful) {
                _email.value = email
            } else {
                _email.value = null
            }

        }


    }

    fun storePassword(password: String) {
        viewModelScope.launch {
            _password.value = password
        }
    }


    fun storeUserInfo(firstName: String, lastName: String) {
        viewModelScope.launch {
            _userInfo.value = Pair(firstName, lastName)
        }

    }


    fun clearAll() {
        _email.value = null
        _phone.value = null
        _password.value = null
        _userInfo.value = null
    }


    fun registerUserAccount() {
        viewModelScope.launch {

            val response = service.createAccount(
                userInfo.value!!.first,
                userInfo.value!!.second,
                email.value.toString(),
                password.value.toString(),
                phone.value.toString()
            )
            if (response.isSuccessful) {
                _responseResult.value = 0
            } else {
                val errorResponse = getModelFromResponseErrorBody(response)
                _responseResult.value = errorResponse.detail.statusCode
            }
        }
    }


}