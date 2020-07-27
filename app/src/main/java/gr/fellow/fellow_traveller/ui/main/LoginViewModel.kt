package gr.fellow.fellow_traveller.ui.main

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.ui.help.BaseViewModel
import gr.fellow.fellow_traveller.ui.help.SingleLiveEvent
import gr.fellow.fellow_traveller.usecase.CheckUserEmailUseCase
import gr.fellow.fellow_traveller.usecase.CheckUserPhoneUseCase
import gr.fellow.fellow_traveller.usecase.LoginUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class LoginViewModel
@ViewModelInject
constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {


    private val _result = SingleLiveEvent<Boolean>()
    val result: LiveData<Boolean> = _result


    fun login(userName: String, password: String) {
        viewModelScope.launch {
            when (val response = loginUseCase(userName, password)) {
                is ResultWrapper.Success ->
                    _result.value = true
                is ResultWrapper.Error ->
                    _error.value = response.error.msg
            }
        }
    }
}