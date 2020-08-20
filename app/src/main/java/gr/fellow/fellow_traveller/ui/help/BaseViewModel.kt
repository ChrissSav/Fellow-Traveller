package gr.fellow.fellow_traveller.ui.help

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.BaseApiException
import gr.fellow.fellow_traveller.data.NoInternetException
import gr.fellow.fellow_traveller.utils.ACCESS_DENIED
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    protected val _error = SingleLiveEvent<Int>()
    val error: LiveData<Int> = _error

    protected val _load = SingleLiveEvent<Boolean>()
    val load: LiveData<Boolean> = _load

    var loadTest = MutableLiveData<Boolean>()

    fun launch(load: Boolean = false, function: suspend () -> Unit) {
        viewModelScope.launch {
            _load.value = load

            try {
                function.invoke()
            } catch (e: Exception) {
                handleError(e)
            }
            _load.value = false
        }
    }


    fun launch(delayTime: Int, function: suspend () -> Unit) {
        viewModelScope.launch {
            delay(delayTime.toLong())
            try {
                function.invoke()
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    fun launch(delayTime: Int, load: Boolean, function: suspend () -> Unit) {
        viewModelScope.launch {
            loadTest.value = load
            delay(delayTime.toLong())
            try {
                function.invoke()
            } catch (e: Exception) {
                handleError(e)
            }
            loadTest.value = false
        }
    }

    private fun handleError(e: Exception) {
        when (e) {
            is BaseApiException -> when (e.code) {
                ACCESS_DENIED -> {
                    _error.value = R.string.ERROR_API_UNAUTHORIZED
                }
                else -> {
                    _error.value = R.string.ERROR_API_UNREACHABLE
                }
            }
            is NoInternetException -> _error.value = R.string.ERROR_INTERNET_CONNECTION
            else -> _error.value = R.string.ERROR_SOMETHING_WRONG
        }
    }
}