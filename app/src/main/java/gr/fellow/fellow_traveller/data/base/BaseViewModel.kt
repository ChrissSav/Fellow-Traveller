package gr.fellow.fellow_traveller.data.base

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
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    val error = SingleLiveEvent<ErrorMessage>()
    val load = MutableLiveData<Boolean>()


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

    fun launchWithLiveData(shouldLoad: Boolean = false, liveData: MutableLiveData<Boolean>, function: suspend () -> Unit) {
        viewModelScope.launch {
            liveData.value = shouldLoad
            try {
                function.invoke()
            } catch (e: Exception) {
                handleError(e)
            }
            liveData.value = false
        }
    }


    fun setLoad(shouldLoad: Boolean) {
        load.value = shouldLoad
    }

    fun setErrorMessage(errorMsg: Int) {
        error.value = internalError(errorMsg)
    }

    fun handleError(e: Exception) {
        e.printStackTrace()
        when (e) {
            is BaseApiException -> when (e.code) {
                ACCESS_DENIED -> {
                    error.value = internalError(R.string.ERROR_API_UNAUTHORIZED)
                }
                else -> {
                    error.value = internalError(R.string.ERROR_API_UNREACHABLE)
                }
            }
            is NoInternetException -> {
                error.value = internalError(R.string.ERROR_INTERNET_CONNECTION)
            }
            is UnauthorizedException -> {
                error.value = internalError(R.string.ERROR_API_UNAUTHORIZED)
            }
            else -> {
                error.value = internalError(R.string.ERROR_SOMETHING_WRONG)
            }
        }
    }

}