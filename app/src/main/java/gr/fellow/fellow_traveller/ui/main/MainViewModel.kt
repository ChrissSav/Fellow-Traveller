package gr.fellow.fellow_traveller.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gr.fellow.fellow_traveller.data.base.BaseViewModel
import gr.fellow.fellow_traveller.data.base.SingleLiveEvent
import gr.fellow.fellow_traveller.usecase.auth.LoginUseCase
import gr.fellow.fellow_traveller.usecase.register.RegisterUserLocalUseCase


class MainViewModel
@ViewModelInject
constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUserLocalUseCase: RegisterUserLocalUseCase
) : BaseViewModel() {

    private val _loginResult = SingleLiveEvent<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult


    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password


    fun login(userName: String, password: String) {
        launch(true) {
            val response = loginUseCase(userName, password)
            registerUserLocalUseCase(response)
            _loginResult.value = true
        }
    }
}