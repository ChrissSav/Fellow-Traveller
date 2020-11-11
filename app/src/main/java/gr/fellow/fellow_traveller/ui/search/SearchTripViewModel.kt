package gr.fellow.fellow_traveller.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.data.base.BaseViewModel
import gr.fellow.fellow_traveller.data.base.SingleLiveEvent
import gr.fellow.fellow_traveller.domain.SearchTripFilter
import gr.fellow.fellow_traveller.domain.externalError
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.domain.trip.TripSearch
import gr.fellow.fellow_traveller.framework.network.google.model.PlaceModel
import gr.fellow.fellow_traveller.usecase.trips.BookTripUseCase
import gr.fellow.fellow_traveller.usecase.trips.SearchTripsUseCase
import kotlinx.coroutines.delay


class SearchTripViewModel
@ViewModelInject
constructor(
    private val searchTripsUseCase: SearchTripsUseCase,
    private val bookTripUseCase: BookTripUseCase
) : BaseViewModel() {

    val loadResults = MutableLiveData<Boolean>()


    private val _tripBook = SingleLiveEvent<TripInvolved>()
    val tripBook: LiveData<TripInvolved> = _tripBook

    private val _destinations = MutableLiveData<Pair<PlaceModel?, PlaceModel?>>()
    val destinations: LiveData<Pair<PlaceModel?, PlaceModel?>> = _destinations


    private val _searchFilter = SingleLiveEvent<SearchTripFilter>()
    val searchFilter: LiveData<SearchTripFilter> = _searchFilter

    private val _resultTrips = SingleLiveEvent<MutableList<TripSearch>>()
    val resultTrips: LiveData<MutableList<TripSearch>> = _resultTrips

    var deleteTripId: String? = null

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
        launch {
            loadResults.value = true
            _searchFilter.value?.let { searchFilters ->
                when (val response = searchTripsUseCase(searchFilters)) {
                    is ResultWrapper.Success -> {
                        _resultTrips.value = response.data
                    }
                    is ResultWrapper.Error -> {
                        error.value = externalError(response.error)
                    }
                }
            }
            loadResults.value = false
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


    fun bookTrip(tripId: String, seats: Int, pet: Boolean) {
        launch(true) {
            when (val response = bookTripUseCase(tripId, seats, pet)) {
                is ResultWrapper.Success -> {
                    _tripBook.value = response.data
                }
                is ResultWrapper.Error -> {
                    deleteTripId = tripId
                    error.value = externalError(response.error)
                }
            }
        }

    }


    fun handleErrorBook(tripId: String) {
        launch {
            delay(200)
            val tempTrips = mutableListOf<TripSearch>()
            tempTrips.addAll(_resultTrips.value ?: emptyList())
            val index = tempTrips.indexOfFirst { it.id == tripId }
            if (index != -1) {
                tempTrips.removeAt(index)
                _resultTrips.value = mutableListOf()
            }

        }

    }

}