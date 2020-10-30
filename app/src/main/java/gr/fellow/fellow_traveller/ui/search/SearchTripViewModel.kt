package gr.fellow.fellow_traveller.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gr.fellow.fellow_traveller.data.ResultWrapperSecond
import gr.fellow.fellow_traveller.data.base.BaseViewModel
import gr.fellow.fellow_traveller.data.base.SingleLiveEvent
import gr.fellow.fellow_traveller.domain.SearchFilters
import gr.fellow.fellow_traveller.domain.externalError
import gr.fellow.fellow_traveller.domain.trip.TripSearch
import gr.fellow.fellow_traveller.framework.network.google.model.PlaceModel
import gr.fellow.fellow_traveller.usecase.trips.BookTripUseCase
import gr.fellow.fellow_traveller.usecase.trips.SearchTripsUseCase


class SearchTripViewModel
@ViewModelInject
constructor(
    private val searchTripsUseCase: SearchTripsUseCase,
    private val bookTripUseCase: BookTripUseCase
) : BaseViewModel() {

    val finish = MutableLiveData<Boolean>()


    private val _tripBook = SingleLiveEvent<TripSearch>()
    val tripBook: LiveData<TripSearch> = _tripBook

    private val _destinationFrom = MutableLiveData<PlaceModel>()
    val destinationFrom: LiveData<PlaceModel> = _destinationFrom

    private val _destinationTo = MutableLiveData<PlaceModel>()
    val destinationTo: LiveData<PlaceModel> = _destinationTo

    private val _searchFilter = MutableLiveData<SearchFilters>()
    val searchFilter: LiveData<SearchFilters> = _searchFilter

    private val _resultTrips = MutableLiveData<MutableList<TripSearch>>()
    val resultTrips: LiveData<MutableList<TripSearch>> = _resultTrips

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
        launchSecond(true) {
            _searchFilter.value?.let { searchFilters ->
                when (val response = searchTripsUseCase(searchFilters)) {
                    is ResultWrapperSecond.Success -> {
                        _resultTrips.value = response.data
                    }
                    is ResultWrapperSecond.Error -> {
                        errorSecond.value = externalError(response.error)
                    }
                }
                filterFlag = false
            }
        }
    }


    /* fun bookTrip(tripId: Int, bags: Int, pet: Boolean) {
         launch(true) {
             when (val response = bookTripUseCase(tripId, bags, pet)) {
                 is ResultWrapper.Success -> {
                     _tripBook.value = response.data
                 }
                 is ResultWrapper.Error -> {
                     error.value =
                         when (response.error.code) {
                             606 ->
                                 R.string.ERROR_TRIP_NOT_AVAILABLE_LUGGAGE
                             607 ->
                                 R.string.ERROR_TRIP_CHECK_PET_ACCEPTS
                             else ->
                                 R.string.ERROR_SOMETHING_WRONG
                         }
                 }
             }
         }

     }
 */

    fun swapDestinations() {
        launch {
            val temp = _searchFilter.value?.copy()
            temp?.let {
                val tempDestination = _destinationTo.value!!.copy()
                _destinationTo.value = _destinationFrom.value!!.copy()
                _destinationFrom.value = tempDestination

                it.latitudeFrom = _destinationFrom.value?.latitude!!
                it.longitudeFrom = _destinationFrom.value?.longitude!!
                it.latitudeTo = _destinationTo.value?.latitude!!
                it.longitudeTo = _destinationTo.value?.longitude!!
                _searchFilter.value = it
                getTrips()
            }
        }
    }

    fun finish() {
        finish.value = true
    }

}