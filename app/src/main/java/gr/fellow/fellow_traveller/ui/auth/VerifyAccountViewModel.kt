package gr.fellow.fellow_traveller.ui.auth

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import gr.fellow.fellow_traveller.data.base.BaseViewModel
import gr.fellow.fellow_traveller.data.base.SingleLiveEvent
import gr.fellow.fellow_traveller.usecase.auth.VerifyAccountUseCase
import kotlinx.coroutines.delay


class VerifyAccountViewModel
@ViewModelInject
constructor(
    private val verifyAccountUseCase: VerifyAccountUseCase
) : BaseViewModel() {

    private val _success = SingleLiveEvent<Boolean>()
    val success: LiveData<Boolean> = _success


    fun verify(token: String) {
        launch(true) {
            delay(500)
            val response = verifyAccountUseCase(token)
            _success.value = true

        }
    }

}