package gr.fellow.fellow_traveller.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import gr.fellow.fellow_traveller.framework.network.google.model.PlaceModel
import gr.fellow.fellow_traveller.ui.help.BaseViewModel
import gr.fellow.fellow_traveller.ui.help.SingleLiveEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
class SearchTripViewModel
@ViewModelInject
constructor() : BaseViewModel() {


    private val _destinationFrom = SingleLiveEvent<PlaceModel>()
    val destinationFrom: LiveData<PlaceModel> = _destinationFrom

    private val _destinationTo = SingleLiveEvent<PlaceModel>()
    val destinationTo: LiveData<PlaceModel> = _destinationTo


    fun setDestinationFrom(place: PlaceModel) {
        launch {
            _destinationFrom.value = place
        }
    }

    fun setDestinationTo(place: PlaceModel) {
        launch {
            _destinationTo.value = place
        }
    }

}