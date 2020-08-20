package gr.fellow.fellow_traveller.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.domain.SearchFilters
import gr.fellow.fellow_traveller.framework.network.fellow.response.TripResponse
import gr.fellow.fellow_traveller.framework.network.google.model.PlaceModel
import gr.fellow.fellow_traveller.ui.help.BaseViewModel
import gr.fellow.fellow_traveller.usecase.trips.SearchTripsUseCase


class SearchTripViewModel
@ViewModelInject
constructor(
    private val searchTripsUseCase: SearchTripsUseCase
) : BaseViewModel() {


    private val _destinationFrom = MutableLiveData<PlaceModel>()
    val destinationFrom: LiveData<PlaceModel> = _destinationFrom

    private val _destinationTo = MutableLiveData<PlaceModel>()
    val destinationTo: LiveData<PlaceModel> = _destinationTo

    private val _searchFilter = MutableLiveData<SearchFilters>()
    val searchFilter: LiveData<SearchFilters> = _searchFilter

    private val _resultTrips = MutableLiveData<MutableList<TripResponse>>()
    val resultTrips: LiveData<MutableList<TripResponse>> = _resultTrips

    var filterFlag: Boolean = false

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

    fun updateFilter() {
        launch {
            _searchFilter.value =
                SearchFilters(
                    latitudeFrom = _destinationFrom.value!!.latitude,
                    longitudeFrom = _destinationFrom.value!!.longitude,
                    latitudeTo = _destinationTo.value!!.latitude,
                    longitudeTo = _destinationTo.value!!.longitude
                )
            filterFlag = true
        }
    }

    fun updateFilter(filter: SearchFilters) {
        _searchFilter.value = filter
        filterFlag = true
    }

    fun getTrips() {
        launch(300, true) {
            _searchFilter.value?.let { searchFilters ->
                when (val response = searchTripsUseCase(searchFilters)) {
                    is ResultWrapper.Success -> {
                        _resultTrips.value = response.data.toMutableList()
                    }
                    is ResultWrapper.Error -> {
                        _error.value = R.string.ERROR_SOMETHING_WRONG
                    }
                }
                filterFlag = false
            }
        }
    }

}