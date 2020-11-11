package gr.fellow.fellow_traveller.ui.user

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.data.base.BaseViewModel
import gr.fellow.fellow_traveller.domain.externalError
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
            when (val response = getUserInfoByIdUseCase(userId)) {
                is ResultWrapper.Success -> {
                    _user.value = response.data
                }
                is ResultWrapper.Error -> {
                    error.value = externalError(response.error)
                }
            }
        }
    }

}