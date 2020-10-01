package gr.fellow.fellow_traveller.ui.register

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.ui.help.BaseViewModel
import gr.fellow.fellow_traveller.ui.help.SingleLiveEvent
import gr.fellow.fellow_traveller.usecase.register.CheckUserEmailUseCase
import gr.fellow.fellow_traveller.usecase.register.CheckUserPhoneUseCase
import gr.fellow.fellow_traveller.usecase.register.RegisterUserLocalUseCase
import gr.fellow.fellow_traveller.usecase.register.RegisterUserUseCase


class RegisterViewModel
@ViewModelInject
constructor(
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

        launch(true) {

            when (val response = checkUserPhoneUseCase(phone)) {
                is ResultWrapper.Success ->
                    _phone.value = phone
                is ResultWrapper.Error ->
                    _error.value = R.string.ERROR_PHONE_ALREADY_EXISTS
            }
        }


    }


    fun checkUserEmail(email: String) {

        launch(true) {
            when (val response = checkUserEmailUseCase(email)) {
                is ResultWrapper.Success ->
                    _email.value = email
                is ResultWrapper.Error ->
                    _error.value = R.string.ERROR_EMAIL_ALREADY_EXISTS
            }

        }


    }

    fun storePassword(password: String) {
        launch(true) {
            _password.value = password
        }
    }


    fun registerUser() {
        launch(true) {

            if (userInfo.value?.first!!.isEmpty() && userInfo.value?.second!!.isEmpty())
                _error.value = R.string.ERROR_FIELDS_REQUIRE
            else {
                when (val response = registerUserUseCase(
                    userInfo.value!!.first, userInfo.value!!.second, email.value.toString(),
                    password.value.toString(), phone.value.toString()
                )) {
                    is ResultWrapper.Success -> {
                        registerUserLocalUseCase(response.data)
                        _finish.value = true
                    }
                    is ResultWrapper.Error -> {
                        when (response.error.code) {
                            200 ->
                                _error.value = R.string.ERROR_PHONE_ALREADY_EXISTS
                            201 ->
                                _error.value = R.string.ERROR_EMAIL_ALREADY_EXISTS
                        }
                    }
                }
            }
        }
    }

    fun storeUserInfo(firstName: String, lastName: String) {
        _userInfo.value = Pair(firstName, lastName)
    }

}