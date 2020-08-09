package gr.fellow.fellow_traveller.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gr.fellow.fellow_traveller.framework.network.google.model.PlaceModel
import gr.fellow.fellow_traveller.ui.help.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
class SearchTripViewModel
@ViewModelInject
constructor() : BaseViewModel() {


    private val _destinationFrom = MutableLiveData<PlaceModel>()
    val destinationFrom: LiveData<PlaceModel> = _destinationFrom

    private val _destinationTo = MutableLiveData<PlaceModel>()
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