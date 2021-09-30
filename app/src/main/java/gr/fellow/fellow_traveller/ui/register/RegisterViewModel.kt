package gr.fellow.fellow_traveller.ui.register

import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import gr.fellow.fellow_traveller.data.base.BaseViewModel
import gr.fellow.fellow_traveller.data.base.SingleLiveEvent
import gr.fellow.fellow_traveller.usecase.register.RegisterUserUseCase
import gr.fellow.fellow_traveller.utils.RESENT_EMAIL
import gr.fellow.fellow_traveller.utils.set


class RegisterViewModel
@ViewModelInject
constructor(
    private var sharedPreferences: SharedPreferences,
    private val registerUserUseCase: RegisterUserUseCase,
) : BaseViewModel() {

    private val _email = SingleLiveEvent<String>()
    val email: LiveData<String> = _email

    fun registerUser(firstName: String, lastName: String, email: String, password: String) {
        launchAfter(_email) {
            registerUserUseCase(firstName, lastName, email, password)
            sharedPreferences[RESENT_EMAIL] = email
            return@launchAfter email
        }
    }


}