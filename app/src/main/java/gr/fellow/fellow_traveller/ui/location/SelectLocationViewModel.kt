package gr.fellow.fellow_traveller.ui.location

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import gr.fellow.fellow_traveller.data.base.BaseViewModel
import gr.fellow.fellow_traveller.data.base.SingleLiveEvent
import gr.fellow.fellow_traveller.domain.trip.Destination
import gr.fellow.fellow_traveller.framework.network.google.model.PlaceModel
import gr.fellow.fellow_traveller.usecase.trip.GetPlaceAutocomplete

class SelectLocationViewModel
@ViewModelInject
constructor(
    private val getPlaceAutocomplete: GetPlaceAutocomplete,
) : BaseViewModel() {

    private val _destinations = SingleLiveEvent<MutableList<Destination>>()
    val destinations: LiveData<MutableList<Destination>> = _destinations


    private val _place = SingleLiveEvent<PlaceModel>()
    val place: LiveData<PlaceModel> = _place

    fun getAutocomplete(title: String, type: String) {
        launch {
            _destinations.value = getPlaceAutocomplete(title, type)
        }
    }
}