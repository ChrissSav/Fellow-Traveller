package gr.fellow.fellow_traveller.ui.forgotPassword

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.data.base.BaseViewModel
import gr.fellow.fellow_traveller.data.base.SingleLiveEvent
import gr.fellow.fellow_traveller.domain.externalError
import gr.fellow.fellow_traveller.usecase.auth.ForgotPasswordUserCase
import gr.fellow.fellow_traveller.usecase.auth.ResetPasswordUserCase

class ForgotPasswordViewModel
@ViewModelInject
constructor(
    private val forgotPasswordUserCase: ForgotPasswordUserCase,
    private val resetPasswordUserCase: ResetPasswordUserCase
) : BaseViewModel() {


    private lateinit var email: String

    private val _successResetPassword = SingleLiveEvent<Boolean>()
    val successResetPassword: LiveData<Boolean> = _successResetPassword

    private val _successForgotRequest = SingleLiveEvent<Boolean>()
    val successForgotRequest: LiveData<Boolean> = _successForgotRequest

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password


    fun storePassword(password: String) {
        _password.value = password
    }

    fun forgotPassword(email: String) {
        launch(true) {

            when (val response = forgotPasswordUserCase(email)) {
                is ResultWrapper.Success -> {
                    this.email = email
                    _successForgotRequest.value = true
                }
                is ResultWrapper.Error -> {
                    error.value = externalError(response.error)
                }
            }
        }
    }


    fun resetPassword(code: String) {
        launch(true) {
            when (val response = resetPasswordUserCase(email, code, _password.value.toString())) {
                is ResultWrapper.Success -> {
                    _successResetPassword.value = true
                }
                is ResultWrapper.Error -> {
                    error.value = externalError(response.error)
                }
            }
        }
    }

    fun forgotPassword() {
        launch(true) {
            when (val response = forgotPasswordUserCase(email)) {
                is ResultWrapper.Success -> {
                    _successForgotRequest.value = true
                }
                is ResultWrapper.Error -> {
                    error.value = externalError(response.error)
                }
            }
        }
    }

}