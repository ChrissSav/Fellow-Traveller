package gr.fellow.fellow_traveller.ui.location

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import gr.fellow.fellow_traveller.data.base.BaseViewModel
import gr.fellow.fellow_traveller.data.base.SingleLiveEvent
import gr.fellow.fellow_traveller.domain.trip.Destination
import gr.fellow.fellow_traveller.usecase.trip.GetPlaceAutocomplete
import gr.fellow.fellow_traveller.usecase.trip.GetPlaceDetails

class SelectLocationViewModel
@ViewModelInject
constructor(
    private val getPlaceAutocomplete: GetPlaceAutocomplete,
    private val getPlaceDetails: GetPlaceDetails
) : BaseViewModel() {

    private val _destinations = SingleLiveEvent<MutableList<Destination>>()
    val destinations: LiveData<MutableList<Destination>> = _destinations


    private val _place = SingleLiveEvent<Destination>()
    val place: LiveData<Destination> = _place


    fun getAutocomplete(title: String, type: String) {
        launch {
            _destinations.value = getPlaceAutocomplete(title, type)
        }
    }

    fun onSelect(id: String, type: String) {
        launch {
            _place.value = getPlaceDetails(id, type)
        }
    }
}