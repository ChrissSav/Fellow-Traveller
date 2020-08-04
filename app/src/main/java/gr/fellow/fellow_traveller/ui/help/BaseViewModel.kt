package gr.fellow.fellow_traveller.ui.help

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    protected  val _error = SingleLiveEvent<String>()
    val error: LiveData<String> = _error
}