package gr.fellow.fellow_traveller.ui.forgotPassword

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gr.fellow.fellow_traveller.data.ResultWrapperSecond
import gr.fellow.fellow_traveller.domain.externalError
import gr.fellow.fellow_traveller.ui.help.BaseViewModel
import gr.fellow.fellow_traveller.ui.help.SingleLiveEvent
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
        launchSecond(true) {

            when (val response = forgotPasswordUserCase(email)) {
                is ResultWrapperSecond.Success -> {
                    this.email = email
                    _successForgotRequest.value = true
                }
                is ResultWrapperSecond.Error -> {
                    errorSecond.value = externalError(response.error)
                }
            }
        }
    }


    fun resetPassword(code: String) {
        launchSecond(true) {
            when (val response = resetPasswordUserCase(email, code, _password.value.toString())) {
                is ResultWrapperSecond.Success -> {
                    _successResetPassword.value = true
                }
                is ResultWrapperSecond.Error -> {
                    errorSecond.value = externalError(response.error)
                }
            }
        }
    }

    fun forgotPassword() {
        launchSecond(true) {
            when (val response = forgotPasswordUserCase(email)) {
                is ResultWrapperSecond.Success -> {
                    _successForgotRequest.value = true
                }
                is ResultWrapperSecond.Error -> {
                    errorSecond.value = externalError(response.error)
                }
            }
        }
    }

}