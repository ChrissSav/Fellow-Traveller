package gr.fellow.fellow_traveller.ui.help

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.BaseApiException
import gr.fellow.fellow_traveller.utils.INTERNET_ERROR
import gr.fellow.fellow_traveller.utils.SOMETHING_WORNG
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class BaseViewModel(private val context: Context) : ViewModel() {

    protected val _error = SingleLiveEvent<String>()
    val error: LiveData<String> = _error

    protected val _load = SingleLiveEvent<Boolean>()
    val load: LiveData<Boolean> = _load


    fun launch(load: Boolean, function: suspend () -> Unit) {
        viewModelScope.launch {
            _load.value = load

            try {
                function.invoke()
            } catch (e: BaseApiException) {
                when (e.code) {
                    SOMETHING_WORNG -> {
                        _error.value = context.resources.getString(R.string.ERROR_API_UNREACHABLE)
                    }
                    INTERNET_ERROR -> {
                        _error.value = context.resources.getString(R.string.ERROR_INTERNET_CONNECTION)
                    }
                }
            }
            _load.value = false
        }
    }


    fun launch(function: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                function.invoke()
            } catch (e: BaseApiException) {
                when (e.code) {
                    SOMETHING_WORNG -> {
                        _error.value = context.resources.getString(R.string.ERROR_API_UNREACHABLE)
                    }
                    INTERNET_ERROR -> {
                        _error.value = context.resources.getString(R.string.ERROR_INTERNET_CONNECTION)
                    }
                }
            }
        }
    }

    fun launch(delayTime: Int, function: suspend () -> Unit) {
        viewModelScope.launch {
            delay(delayTime.toLong())
            try {
                function.invoke()
            } catch (e: BaseApiException) {
                when (e.code) {
                    SOMETHING_WORNG -> {
                        _error.value = context.resources.getString(R.string.ERROR_API_UNREACHABLE)
                    }
                    INTERNET_ERROR -> {
                        _error.value = context.resources.getString(R.string.ERROR_INTERNET_CONNECTION)
                    }
                }
            }
        }
    }
}