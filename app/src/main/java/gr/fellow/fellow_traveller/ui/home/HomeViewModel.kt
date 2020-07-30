package gr.fellow.fellow_traveller.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity
import gr.fellow.fellow_traveller.ui.help.BaseViewModel
import gr.fellow.fellow_traveller.usecase.LoadUserInfoUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
class HomeViewModel
@ViewModelInject
constructor(
    private val loadUserInfoUseCase: LoadUserInfoUseCase
) : BaseViewModel() {


    private val _user = MutableLiveData<RegisteredUserEntity>()
    val user: LiveData<RegisteredUserEntity> = _user


    fun loadUserInfo() {
        viewModelScope.launch {
            _user.value = loadUserInfoUseCase()
        }
    }

}