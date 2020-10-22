package gr.fellow.fellow_traveller.ui.help

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.BaseApiException
import gr.fellow.fellow_traveller.data.NoInternetException
import gr.fellow.fellow_traveller.data.UnauthorizedException
import gr.fellow.fellow_traveller.domain.ErrorMessage
import gr.fellow.fellow_traveller.domain.internalError
import gr.fellow.fellow_traveller.utils.ACCESS_DENIED
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    val error = SingleLiveEvent<Int>()
    val load = MutableLiveData<Boolean>()


    val errorSecond = SingleLiveEvent<ErrorMessage>()

    fun launch(shouldLoad: Boolean = false, function: suspend () -> Unit) {
        viewModelScope.launch {
            load.value = shouldLoad
            try {
                function.invoke()
            } catch (e: Exception) {
                handleError(e)
            }
            load.value = false
        }
    }


    fun launch(delayTime: Int, shouldLoad: Boolean, function: suspend () -> Unit) {
        viewModelScope.launch {
            load.value = shouldLoad
            delay(delayTime.toLong())
            try {
                function.invoke()
            } catch (e: Exception) {
                handleError(e)
            }
            load.value = false
        }
    }

    fun setError(errorMsg: Int) {
        error.value = errorMsg
    }

    fun setSecondError(errorMsg: Int) {
        errorSecond.value = internalError(errorMsg)
    }

    private fun handleError(e: Exception) {
        when (e) {
            is BaseApiException -> when (e.code) {
                ACCESS_DENIED -> {
                    error.value = R.string.ERROR_API_UNAUTHORIZED
                }
                else -> {
                    error.value = R.string.ERROR_API_UNREACHABLE
                }
            }
            is NoInternetException -> error.value = R.string.ERROR_INTERNET_CONNECTION
            is UnauthorizedException -> error.value = R.string.ERROR_API_UNAUTHORIZED
            else -> error.value = R.string.ERROR_SOMETHING_WRONG
        }
    }


    /*** SECONDS ****/
    private fun handleErrorSecond(e: Exception) {
        when (e) {
            is BaseApiException -> when (e.code) {
                ACCESS_DENIED -> {
                    errorSecond.value = internalError(R.string.ERROR_API_UNAUTHORIZED)
                }
                else -> {
                    errorSecond.value = internalError(R.string.ERROR_API_UNREACHABLE)
                }
            }
            is NoInternetException -> {
                errorSecond.value = internalError(R.string.ERROR_INTERNET_CONNECTION)
            }
            is UnauthorizedException -> {
                errorSecond.value = internalError(R.string.ERROR_API_UNAUTHORIZED)
            }


            else -> {
                errorSecond.value = internalError(R.string.ERROR_SOMETHING_WRONG)
            }
        }
    }


    fun launchSecond(shouldLoad: Boolean = false, function: suspend () -> Unit) {
        viewModelScope.launch {
            load.value = shouldLoad
            try {
                function.invoke()
            } catch (e: Exception) {
                handleErrorSecond(e)
            }
            load.value = false
        }
    }
}