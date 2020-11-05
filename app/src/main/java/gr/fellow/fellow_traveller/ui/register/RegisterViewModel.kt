package gr.fellow.fellow_traveller.ui.register

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.data.base.BaseViewModel
import gr.fellow.fellow_traveller.data.base.SingleLiveEvent
import gr.fellow.fellow_traveller.domain.externalError
import gr.fellow.fellow_traveller.domain.internalError
import gr.fellow.fellow_traveller.usecase.register.CheckUserEmailUseCase
import gr.fellow.fellow_traveller.usecase.register.RegisterUserUseCase


class RegisterViewModel
@ViewModelInject
constructor(
    private val checkUserEmailUseCase: CheckUserEmailUseCase,
    private val registerUserUseCase: RegisterUserUseCase
) : BaseViewModel() {

    private val _email = SingleLiveEvent<String>()
    val email: LiveData<String> = _email


    private val _password = SingleLiveEvent<String>()
    val password: LiveData<String> = _password


    private val _userInfo = MutableLiveData<Pair<String, String>>()
    val userInfo: MutableLiveData<Pair<String, String>> = _userInfo


    private val _finish = SingleLiveEvent<Boolean>()
    val finish: LiveData<Boolean> = _finish


    fun checkUserEmail(email: String) {

        launch(true) {
            when (checkUserEmailUseCase(email)) {
                is ResultWrapper.Success ->
                    _email.value = email
                is ResultWrapper.Error ->
                    error.value = internalError(R.string.ERROR_EMAIL_ALREADY_EXISTS)
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

            val firstName = userInfo.value?.first.toString()
            val lastName = userInfo.value?.second.toString()

            when (val response = registerUserUseCase(
                firstName, lastName, email.value.toString(), password.value.toString()
            )) {
                is ResultWrapper.Success -> {
                    _finish.value = true
                }
                is ResultWrapper.Error -> {
                    error.value = externalError(response.error)
                }
            }

        }
    }

    fun storeUserInfo(firstName: String, lastName: String) {
        _userInfo.value = Pair(firstName, lastName)
    }

}