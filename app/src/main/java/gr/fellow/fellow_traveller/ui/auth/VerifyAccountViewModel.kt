package gr.fellow.fellow_traveller.ui.auth

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import gr.fellow.fellow_traveller.data.ResultWrapperSecond
import gr.fellow.fellow_traveller.domain.externalError
import gr.fellow.fellow_traveller.ui.help.BaseViewModel
import gr.fellow.fellow_traveller.ui.help.SingleLiveEvent
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
        launchSecond(true) {
            delay(500)
            when (val response = verifyAccountUseCase(token)) {
                is ResultWrapperSecond.Success ->
                    _success.value = true
                is ResultWrapperSecond.Error ->
                    errorSecond.value = externalError(response.error)
            }
        }
    }

}