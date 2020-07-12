package com.example.fellowtravellerbeta.ui.register.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fellowtravellerbeta.data.network.ApiRepository
import kotlinx.coroutines.launch

class RegisterSharedViewModel(private val service: ApiRepository) : ViewModel() {


    private val _phone = MutableLiveData<String>()
    val phone: LiveData<String> = _phone

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email


    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password


    private val _userInfo = MutableLiveData<Pair<String, String>>()
    val userInfo: LiveData<Pair<String, String>> = _userInfo


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
        _password.value = password

    }


    fun storeUserInfo(firstName: String, lastName: String) {
        _userInfo.value = Pair(firstName, lastName)

    }


    fun clearAll() {
        _email.value = null
        _phone.value = null
        _password.value = null
        _userInfo.value = null
    }


    fun registerUser(){
       // val res = service.
    }

}