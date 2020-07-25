package gr.fellow.fellow_traveller.ui.register

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.ui.help.BaseViewModel
import gr.fellow.fellow_traveller.ui.help.SingleLiveEvent
import gr.fellow.fellow_traveller.usecase.CheckUserEmailUseCase
import gr.fellow.fellow_traveller.usecase.CheckUserPhoneUseCase
import gr.fellow.fellow_traveller.usecase.RegisterUserUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class RegisterViewModel
@ViewModelInject
constructor(
    private val context: Context,
    private val checkUserPhoneUseCase: CheckUserPhoneUseCase,
    private val checkUserEmailUseCase: CheckUserEmailUseCase,
    private val registerUserUseCase: RegisterUserUseCase


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
                    _error.value = context.resources.getString(R.string.ERROR_PHONE_ALREADY_EXISTS)
            }
        }


    }


    fun checkUserEmail(email: String) {

        viewModelScope.launch {
            when (val response = checkUserEmailUseCase(email)) {
                is ResultWrapper.Success ->
                    _email.value = email
                is ResultWrapper.Error ->
                    _error.value = context.resources.getString(R.string.ERROR_EMAIL_ALREADY_EXISTS)
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
                when (val res = registerUserUseCase(
                    userInfo.value!!.first,
                    userInfo.value!!.second,
                    email.value.toString(),
                    password.value.toString(),
                    phone.value.toString()
                )) {
                    is ResultWrapper.Error ->
                        _error.value = res.error.msg
                    is ResultWrapper.Success ->
                        _finish.value = true
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