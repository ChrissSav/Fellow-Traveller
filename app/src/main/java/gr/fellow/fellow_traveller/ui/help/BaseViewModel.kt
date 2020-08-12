package gr.fellow.fellow_traveller.ui.help

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    protected  val _error = SingleLiveEvent<String>()
    val error: LiveData<String> = _error

    protected val _load = SingleLiveEvent<Boolean>()
    val load: LiveData<Boolean> = _load

    fun launch(load: Boolean, function: suspend () -> Unit) {
        viewModelScope.launch {
            _load.value = load
            function.invoke()
            _load.value = false
        }
    }

    fun launch(function: suspend () -> Unit) {
        viewModelScope.launch {
            function.invoke()
        }
    }

    fun launch(delayTime: Int, function: suspend () -> Unit) {
        viewModelScope.launch {
            delay(delayTime.toLong())
            function.invoke()
        }
    }
}