package gr.fellow.fellow_traveller.ui.home

import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity
import gr.fellow.fellow_traveller.set
import gr.fellow.fellow_traveller.ui.help.BaseViewModel
import gr.fellow.fellow_traveller.ui.help.SingleLiveEvent
import gr.fellow.fellow_traveller.usecase.LoadUserInfoUseCase
import gr.fellow.fellow_traveller.usecase.LogoutUseCase
import gr.fellow.fellow_traveller.utils.PREFS_AUTH_TOKEN
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
class HomeViewModel
@ViewModelInject
constructor(
    private val loadUserInfoUseCase: LoadUserInfoUseCase,
    private val logoutUseCase: LogoutUseCase

) : BaseViewModel() {


    private val _user = MutableLiveData<RegisteredUserEntity>()
    val user: LiveData<RegisteredUserEntity> = _user


    private val _logout = SingleLiveEvent<Boolean>()
    val logout: LiveData<Boolean> = _logout

    fun loadUserInfo() {
        viewModelScope.launch {
            _user.value = loadUserInfoUseCase()
        }
    }

    fun logOut() {
        viewModelScope.launch {
            logoutUseCase()
            _logout.value = true
        }
    }

}