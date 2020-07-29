package gr.fellow.fellow_traveller.ui.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gr.fellow.fellow_traveller.domain.sigleton.UserInfoSingle
import gr.fellow.fellow_traveller.room.dao.UserAuthDao
import gr.fellow.fellow_traveller.ui.help.SingleLiveEvent
import gr.fellow.fellow_traveller.usecase.register.CheckIfUserIsLoginUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class SplashViewModel
@ViewModelInject
constructor(
    private val checkIfUserIsLoginUseCase: CheckIfUserIsLoginUseCase,
    private val userAuthDao: UserAuthDao,
    private val userInfoSingle: UserInfoSingle
) : ViewModel() {

    private val _result = SingleLiveEvent<Boolean>()
    val result: LiveData<Boolean> = _result


    fun checkUserState() {
        viewModelScope.launch {
            val res = checkIfUserIsLoginUseCase()
            if (res) {
                val temp = userAuthDao.getUserRegistered()
                userInfoSingle.id = temp.id
                userInfoSingle.firstName = temp.firstName
                userInfoSingle.lastName = temp.lastName
                userInfoSingle.picture = temp.picture

            }

            _result.value = res
        }
    }
}