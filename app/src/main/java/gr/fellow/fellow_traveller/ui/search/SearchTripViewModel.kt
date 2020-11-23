package gr.fellow.fellow_traveller.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gr.fellow.fellow_traveller.data.base.BaseViewModel
import gr.fellow.fellow_traveller.data.base.SingleLiveEvent
import gr.fellow.fellow_traveller.domain.SearchTripFilter
import gr.fellow.fellow_traveller.domain.SortAnswerType
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
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

    private var sortOption: SortAnswerType = SortAnswerType.Relevant

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
                val response = searchTripsUseCase(searchFilters)
                _resultTrips.value = response
                setSortOption(sortOption)
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
            try {
                val response = bookTripUseCase(tripId, seats, pet)
                _tripBook.value = response
            } catch (e: Exception) {
                deleteTripId = tripId
                throw e
            }

        }

    }


    fun handleErrorBook(tripId: String) {
        var tempTrips = mutableListOf<TripSearch>()
        tempTrips.addAll(_resultTrips.value ?: emptyList())
        tempTrips = tempTrips.filter { it.id != tripId }.toMutableList()
        _resultTrips.value = mutableListOf()
        _resultTrips.value = tempTrips
        deleteTripId = null
    }

    // TODO Can merge these 3 fun to 1
    fun sortByDate() {
        val sortedList = _resultTrips.value?.sortedWith(compareBy { it.timestamp })?.toMutableList()
        _resultTrips.value = sortedList
        sortOption = SortAnswerType.Relevant

    }

    fun sortByPrice() {
        val sortedList = _resultTrips.value?.sortedWith(compareBy { it.price })?.toMutableList()
        _resultTrips.value = sortedList
        sortOption = SortAnswerType.Price
    }

    fun sortByRate() {
        val sortedList = _resultTrips.value?.sortedWith(compareBy { it.creatorUser.rate })?.toMutableList()
        _resultTrips.value = sortedList
        sortOption = SortAnswerType.Rate
    }

    //Set Up Sort Option, based on previous choice
    private fun setSortOption(sortOption: SortAnswerType) {
        when (sortOption) {
            SortAnswerType.Relevant -> sortByDate()
            SortAnswerType.Price -> sortByPrice()
            else -> sortByRate()
        }

    }


}