package gr.fellow.fellow_traveller.ui.user

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gr.fellow.fellow_traveller.data.base.BaseViewModel
import gr.fellow.fellow_traveller.domain.user.UserInfo
import gr.fellow.fellow_traveller.usecase.user.GetUserInfoByIdUseCase

class UserInfoDetailsViewModel
@ViewModelInject
constructor(
    private val getUserInfoByIdUseCase: GetUserInfoByIdUseCase
) : BaseViewModel() {

    private val _user = MutableLiveData<UserInfo>()
    val user: LiveData<UserInfo> = _user


    fun loadUserInfo(userId: String) {
        launch(true) {
            val response = getUserInfoByIdUseCase(userId)
            _user.value = response
        }
    }
}