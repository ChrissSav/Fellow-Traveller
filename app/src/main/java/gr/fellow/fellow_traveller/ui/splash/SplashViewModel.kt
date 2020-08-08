package gr.fellow.fellow_traveller.ui.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gr.fellow.fellow_traveller.ui.help.SingleLiveEvent
import gr.fellow.fellow_traveller.usecase.register.CheckIfUserIsLoginUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class SplashViewModel
@ViewModelInject
constructor(
    private val checkIfUserIsLoginUseCase: CheckIfUserIsLoginUseCase
) : ViewModel() {

    private val _result = SingleLiveEvent<Boolean>()
    val result: LiveData<Boolean> = _result


    fun checkUserState() {
        viewModelScope.launch {
            _result.value = checkIfUserIsLoginUseCase()
        }
    }
}