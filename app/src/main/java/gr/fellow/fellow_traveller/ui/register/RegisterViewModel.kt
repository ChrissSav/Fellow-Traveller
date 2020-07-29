package gr.fellow.fellow_traveller.ui.register

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.ui.help.BaseViewModel
import gr.fellow.fellow_traveller.ui.help.SingleLiveEvent
import gr.fellow.fellow_traveller.usecase.register.CheckUserEmailUseCase
import gr.fellow.fellow_traveller.usecase.register.CheckUserPhoneUseCase
import gr.fellow.fellow_traveller.usecase.register.RegisterUserLocalUseCase
import gr.fellow.fellow_traveller.usecase.register.RegisterUserUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class RegisterViewModel
@ViewModelInject
constructor(
    private val context: Context,
    private val checkUserPhoneUseCase: CheckUserPhoneUseCase,
    private val checkUserEmailUseCase: CheckUserEmailUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val registerUserLocalUseCase: RegisterUserLocalUseCase


) : BaseViewModel() {
    private val _phone = SingleLiveEvent<String>()
    val phone: LiveData<String> = _phone

    private val _email = SingleLiveEvent<String>()
    val email: LiveData<String> = _email


    private val _password = SingleLiveEvent<String>()
    val password: LiveData<String> = _password


    private val _userInfo = SingleLiveEvent<Pair<String, String>>()
    val userInfo: LiveData<Pair<String, String>> = _userInfo


    private val _finish = SingleLiveEvent<Boolean>()
    val finish: LiveData<Boolean> = _finish

    fun checkUserPhone(phone: String) {

        viewModelScope.launch {

            when (val response = checkUserPhoneUseCase(phone)) {
                is ResultWrapper.Success ->
                    _phone.value = phone
                is ResultWrapper.Error ->
                    _error.value = response.error.msg
            }
        }


    }


    fun checkUserEmail(email: String) {

        viewModelScope.launch {
            when (val response = checkUserEmailUseCase(email)) {
                is ResultWrapper.Success ->
                    _email.value = email
                is ResultWrapper.Error ->
                    _error.value = response.error.msg
            }

        }


    }

    fun storePassword(password: String) {
        viewModelScope.launch {
            _password.value = password
        }
    }


    fun registerUser() {
        viewModelScope.launch {

            if (userInfo.value?.first!!.isEmpty() && userInfo.value?.second!!.isEmpty())
                _error.value = context.resources.getString(R.string.ERROR_FIELDS_REQUIRE)
            else {
                when (val response = registerUserUseCase(
                    userInfo.value!!.first,
                    userInfo.value!!.second,
                    email.value.toString(),
                    password.value.toString(),
                    phone.value.toString()
                )) {
                    is ResultWrapper.Error ->
                        _error.value = response.error.msg
                    is ResultWrapper.Success ->{
                        registerUserLocalUseCase(response.data)
                        _finish.value = true

                    }
                }
            }
        }
    }

    fun storeUserInfo(firstName: String, lastName: String) {
        viewModelScope.launch {
            _userInfo.value = Pair(firstName, lastName)
        }

    }

}