package gr.fellow.fellow_traveller.ui.rate

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import gr.fellow.fellow_traveller.data.base.BaseViewModel
import gr.fellow.fellow_traveller.data.base.SingleLiveEvent

class RateViewModel
@ViewModelInject
constructor(

) : BaseViewModel() {

    private val _success = SingleLiveEvent<String>()
    val success: LiveData<String> = _success

    fun registerRate(userId: String, comment: String, rate: Float){
        launch {

        }
    }
}