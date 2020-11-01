package gr.fellow.fellow_traveller.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gr.fellow.fellow_traveller.data.ResultWrapperSecond
import gr.fellow.fellow_traveller.data.base.BaseViewModel
import gr.fellow.fellow_traveller.data.base.SingleLiveEvent
import gr.fellow.fellow_traveller.domain.SearchTripFilter
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


    private val _tripBook = SingleLiveEvent<TripSearch>()
    val tripBook: LiveData<TripSearch> = _tripBook

    private val _destinations = MutableLiveData<Pair<PlaceModel?, PlaceModel?>>()
    val destinations: LiveData<Pair<PlaceModel?, PlaceModel?>> = _destinations


    private val _searchFilter = SingleLiveEvent<SearchTripFilter>()
    val searchFilter: LiveData<SearchTripFilter> = _searchFilter

    private val _resultTrips = SingleLiveEvent<MutableList<TripSearch>>()
    val resultTrips: LiveData<MutableList<TripSearch>> = _resultTrips


    fun setDestinationFrom(place: PlaceModel) {
        val temp = _destinations.value?.second
        _destinations.value = Pair(place, temp)
    }

    fun setDestinationTo(place: PlaceModel) {
        val temp = _destinations.value?.first
        _destinations.value = Pair(temp, place)
    }

    fun updateFilter(filter: SearchTripFilter) {
        if (filter != _searchFilter.value)
            _searchFilter.value = filter
    }

    fun updateFilter() {
        _searchFilter.value = SearchTripFilter(
            _destinations.value?.first?.latitude!!.toFloat(),
            _destinations.value?.first?.longitude!!.toFloat(),
            _destinations.value?.second?.latitude!!.toFloat(),
            _destinations.value?.second?.longitude!!.toFloat()
        )

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
            }
        }
    }


    fun swapDestinations() {
        _destinations.value = Pair(_destinations.value?.second, _destinations.value?.first)
        _searchFilter.value = SearchTripFilter(
            _destinations.value?.first?.latitude!!.toFloat(),
            _destinations.value?.first?.longitude!!.toFloat(),
            _destinations.value?.second?.latitude!!.toFloat(),
            _destinations.value?.second?.longitude!!.toFloat()
        )
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

    /*fun swapDestinations() {
        launchSecond {
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
            }
        }
    }*/


}