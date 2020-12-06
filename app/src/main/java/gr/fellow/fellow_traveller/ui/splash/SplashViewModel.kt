package gr.fellow.fellow_traveller.ui.splash

import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.BaseApiException
import gr.fellow.fellow_traveller.data.UnauthorizedException
import gr.fellow.fellow_traveller.data.base.BaseViewModel
import gr.fellow.fellow_traveller.data.base.SingleLiveEvent
import gr.fellow.fellow_traveller.domain.internalError
import gr.fellow.fellow_traveller.usecase.auth.DeleteUserAuthLocalUseCase
import gr.fellow.fellow_traveller.usecase.home.GetUserInfoRemoteUseCase
import gr.fellow.fellow_traveller.usecase.register.RegisterUserLocalUseCase
import gr.fellow.fellow_traveller.utils.PREFS_AUTH_ACCESS_TOKEN
import gr.fellow.fellow_traveller.utils.PREFS_AUTH_REFRESH_TOKEN
import gr.fellow.fellow_traveller.utils.set

class SplashViewModel
@ViewModelInject
constructor(
    private val getUserInfoRemoteUseCase: GetUserInfoRemoteUseCase,
    private val registerUserLocalUseCase: RegisterUserLocalUseCase,
    private val deleteUserAuthLocalUseCase: DeleteUserAuthLocalUseCase,
    private val sharedPrefs: SharedPreferences
) : BaseViewModel() {

    private val _userInfo = SingleLiveEvent<Boolean>()
    val userInfo: LiveData<Boolean> = _userInfo

    private val _finish = MutableLiveData<Pair<Boolean, Boolean>>()
    val finish: LiveData<Pair<Boolean, Boolean>> = _finish


    fun getUserInfo() {
        launch {
            try {
                val response = getUserInfoRemoteUseCase()
                registerUserLocalUseCase(response)
                _userInfo.value = true
            } catch (e: BaseApiException) {
                when (e) {
                    is UnauthorizedException -> {
                        sharedPrefs[PREFS_AUTH_ACCESS_TOKEN] = null
                        sharedPrefs[PREFS_AUTH_REFRESH_TOKEN] = null
                        deleteUserAuthLocalUseCase()
                        error.value = internalError(R.string.ERROR_API_UNAUTHORIZED)
                    }
                    else -> {
                        _userInfo.value = true
                    }
                }
            }
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