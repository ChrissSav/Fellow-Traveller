package gr.fellow.fellow_traveller.ui.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import gr.fellow.fellow_traveller.data.base.BaseViewModel
import gr.fellow.fellow_traveller.data.base.SingleLiveEvent
import gr.fellow.fellow_traveller.usecase.home.GetUserInfoRemoteUseCase
import gr.fellow.fellow_traveller.usecase.register.RegisterUserLocalUseCase

class SplashViewModel
@ViewModelInject
constructor(
    private val getUserInfoRemoteUseCase: GetUserInfoRemoteUseCase,
    private val registerUserLocalUseCase: RegisterUserLocalUseCase
) : BaseViewModel() {

    private val _userInfo = SingleLiveEvent<Boolean>()
    val userInfo: LiveData<Boolean> = _userInfo

    private val _finish = SingleLiveEvent<Pair<Boolean, Boolean>>()
    val finish: LiveData<Pair<Boolean, Boolean>> = _finish


    fun getUserInfo() {
        launch {
            val response = getUserInfoRemoteUseCase()
            registerUserLocalUseCase(response)
            _userInfo.value = true
        }
    }


    fun setFist(first: Boolean) {
        val temp = _finish.value?.second ?: false
        _finish.value = Pair(first, temp)
    }

    fun setSecond(second: Boolean) {
        val temp = _finish.value?.first ?: false
        _finish.value = Pair(temp, second)
    }


}