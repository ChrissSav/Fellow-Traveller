package gr.fellow.fellow_traveller.data.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.BaseApiException
import gr.fellow.fellow_traveller.data.BaseFirebaseException
import gr.fellow.fellow_traveller.data.NoInternetException
import gr.fellow.fellow_traveller.data.UnauthorizedException
import gr.fellow.fellow_traveller.domain.ErrorMessage
import gr.fellow.fellow_traveller.domain.externalError
import gr.fellow.fellow_traveller.domain.internalError
import gr.fellow.fellow_traveller.utils.ACCESS_DENIED
import kotlinx.coroutines.launch
import java.net.UnknownHostException

open class BaseViewModel : ViewModel() {

    val error = SingleLiveEvent<ErrorMessage>()
    val load = MutableLiveData<Boolean>()
    val forceLogOut = MutableLiveData<Boolean>()


    fun launch(shouldLoad: Boolean = false, function: suspend () -> Unit) {
        viewModelScope.launch {
            load.value = shouldLoad
            try {
                function.invoke()
                load.value = false
            } catch (e: Exception) {
                load.value = false
                handleError(e)
            }

        }
    }

    fun <T : Any> launchAfter(mLiveData: SingleLiveEvent<T>, function: suspend () -> T) {
        viewModelScope.launch {
            load.value = true
            try {
                val data = function.invoke()
                load.value = false
                mLiveData.value = data
            } catch (e: Exception) {
                load.value = false
                handleError(e)
            }

        }
    }

    fun <T : Any> launchAfter(mLiveData: MutableLiveData<T>, function: suspend () -> T) {
        viewModelScope.launch {
            load.value = true
            try {
                val data = function.invoke()
                load.value = false
                mLiveData.value = data
            } catch (e: Exception) {
                load.value = false
                handleError(e)
            }

        }
    }


    fun launchWithOutException(shouldLoad: Boolean = false, function: suspend () -> Unit) {
        viewModelScope.launch {
            load.value = shouldLoad
            try {
                function.invoke()
                load.value = false
            } catch (e: Exception) {
                load.value = false
            }

        }
    }

    fun launchWithLiveData(shouldLoad: Boolean = false, liveData: MutableLiveData<Boolean>, function: suspend () -> Unit) {
        viewModelScope.launch {
            liveData.value = shouldLoad
            try {
                function.invoke()
                liveData.value = false
            } catch (e: Exception) {
                liveData.value = false
                handleError(e)
            }
        }
    }


    fun setLoad(shouldLoad: Boolean) {
        load.value = shouldLoad
    }

    fun setErrorMessage(errorMsg: Int) {
        error.value = internalError(errorMsg)
    }


    private fun handleError(e: Exception) {
        e.printStackTrace()
        when (e) {
            is BaseApiException -> {
                if (e.code == null) {
                    error.value = externalError(e.text.toString())
                    return
                }
                when (e.code) {
                    ACCESS_DENIED -> {
                        error.value = internalError(R.string.ERROR_API_UNAUTHORIZED)
                    }
                    else -> {
                        error.value = internalError(R.string.ERROR_API_UNREACHABLE)
                    }
                }
            }
            is NoInternetException -> {
                error.value = internalError(R.string.ERROR_INTERNET_CONNECTION)
            }
            is UnauthorizedException -> {
                forceLogOut.value = true
                error.value = internalError(R.string.ERROR_API_UNAUTHORIZED)
            }
            is UnknownHostException -> {

            }
            is BaseFirebaseException -> {
                error.value = internalError(R.string.ERROR_API_UNAUTHORIZED)
            }
            else -> {
                error.value = internalError(R.string.ERROR_SOMETHING_WRONG)
            }
        }
    }

}