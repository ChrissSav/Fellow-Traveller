package gr.fellow.fellow_traveller.ui.location

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import gr.fellow.fellow_traveller.data.BaseApiException
import gr.fellow.fellow_traveller.framework.network.google.response.PredictionResponse
import gr.fellow.fellow_traveller.ui.help.BaseViewModel
import gr.fellow.fellow_traveller.ui.help.SingleLiveEvent
import gr.fellow.fellow_traveller.usecase.newtrip.GetPlaceFromPlacesUseCase
import kotlinx.coroutines.launch

class SelectLocationViewModel
@ViewModelInject
constructor(
    private val getPlacesUseCase: GetPlaceFromPlacesUseCase
) : BaseViewModel() {

    private val _destinations = SingleLiveEvent<MutableList<PredictionResponse>>()
    val destinations: LiveData<MutableList<PredictionResponse>> = _destinations


    fun getAllDestinations(title: String) {
        viewModelScope.launch {
            try {
                val result = getPlacesUseCase(title)
                if (result.isSuccessful) {
                    result.body()?.let {

                        val res = result.body()?.predictions
                        if (res!!.isNotEmpty()) {
                            _destinations.value = res
                        }


                    }
                }
            } catch (exception: BaseApiException) {
                _error.value = exception.msg
            }

        }
    }
}