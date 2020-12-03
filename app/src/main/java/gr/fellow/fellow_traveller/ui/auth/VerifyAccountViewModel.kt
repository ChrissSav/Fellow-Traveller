package gr.fellow.fellow_traveller.ui.auth

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import gr.fellow.fellow_traveller.data.base.BaseViewModel
import gr.fellow.fellow_traveller.data.base.SingleLiveEvent
import gr.fellow.fellow_traveller.usecase.auth.VerifyAccountUseCase
import gr.fellow.fellow_traveller.usecase.register.RegisterUserLocalUseCase


class VerifyAccountViewModel
@ViewModelInject
constructor(
    private val verifyAccountUseCase: VerifyAccountUseCase,
    private val registerUserLocalUseCase: RegisterUserLocalUseCase
) : BaseViewModel() {

    private val _success = SingleLiveEvent<Boolean>()
    val success: LiveData<Boolean> = _success


    fun verify(token: String) {
        launch(true) {
            val response = verifyAccountUseCase(token)
            registerUserLocalUseCase(response)
            _success.value = true
        }
    }

}