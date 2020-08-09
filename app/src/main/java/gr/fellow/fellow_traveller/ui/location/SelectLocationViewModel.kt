package gr.fellow.fellow_traveller.ui.location

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import gr.fellow.fellow_traveller.data.BaseApiException
import gr.fellow.fellow_traveller.framework.network.google.model.PlaceModel
import gr.fellow.fellow_traveller.framework.network.google.response.PredictionResponse
import gr.fellow.fellow_traveller.ui.help.BaseViewModel
import gr.fellow.fellow_traveller.ui.help.SingleLiveEvent
import gr.fellow.fellow_traveller.usecase.newtrip.GetGeometryFormPlaceUseCase
import gr.fellow.fellow_traveller.usecase.newtrip.GetPlaceFromPlacesUseCase

class SelectLocationViewModel
@ViewModelInject
constructor(
    private val getPlacesUseCase: GetPlaceFromPlacesUseCase,
    private val getGeometryFormPlaceUseCase: GetGeometryFormPlaceUseCase
) : BaseViewModel() {

    private val _destinations = SingleLiveEvent<MutableList<PredictionResponse>>()
    val destinations: LiveData<MutableList<PredictionResponse>> = _destinations


    private val _place = SingleLiveEvent<PlaceModel>()
    val place: LiveData<PlaceModel> = _place

    fun getAllDestinations(title: String) {
        launch {
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

    fun getLanLogForPlace(placeId: String, placeTitle: String) {
        launch {
            try {
                val result = getGeometryFormPlaceUseCase(placeId)
                if (result.isSuccessful) {
                    result.body()?.let {
                        val res = result.body()!!.result.geometry
                        _place.value = PlaceModel(placeId, placeTitle, res.location.lat, res.location.lng)
                    }
                }
            } catch (exception: BaseApiException) {
                _error.value = exception.msg
            }
        }
    }
}