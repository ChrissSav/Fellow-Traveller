package gr.fellow.fellow_traveller.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.ui.help.BaseViewModel
import gr.fellow.fellow_traveller.ui.help.SingleLiveEvent
import gr.fellow.fellow_traveller.usecase.LoginUseCase
import gr.fellow.fellow_traveller.usecase.register.RegisterUserLocalUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class LoginViewModel
@ViewModelInject
constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUserLocalUseCase: RegisterUserLocalUseCase
) : BaseViewModel() {


    private val _result = SingleLiveEvent<Boolean>()
    val result: LiveData<Boolean> = _result


    fun login(userName: String, password: String) {

        launch(true) {
            when (val response = loginUseCase(userName, password)) {
                is ResultWrapper.Success -> {
                    registerUserLocalUseCase(response.data)
                    _result.value = true

                }
                is ResultWrapper.Error ->
                    _error.value = response.error.msg
            }
        }
    }
}