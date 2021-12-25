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
import gr.fellow.fellow_traveller.domain.user.UserBase
import gr.fellow.fellow_traveller.framework.network.google.model.PlaceModel
import gr.fellow.fellow_traveller.usecase.firabase.CreateOrEnterConversationFirebaseUseCase
import gr.fellow.fellow_traveller.usecase.firabase.SendMessageFirebaseUseCase
import gr.fellow.fellow_traveller.usecase.trip.BookTripUseCase
import gr.fellow.fellow_traveller.usecase.trip.SearchTripsUseCase
import kotlinx.coroutines.delay


class SearchTripViewModel
@ViewModelInject
constructor(
    private val searchTripsUseCase: SearchTripsUseCase,
    private val bookTripUseCase: BookTripUseCase,
    private val createOrEnterConversationFirebaseUseCase: CreateOrEnterConversationFirebaseUseCase,
    //Messages
    private val sendMessageFirebaseUseCase: SendMessageFirebaseUseCase,

    ) : BaseViewModel() {

    private var sortOption: SortAnswerType = SortAnswerType.RELEVANT

    val loadResults = MutableLiveData<Boolean>()

    val destinationsIsSet = MutableLiveData<Boolean>()

    private val _tripBook = SingleLiveEvent<TripInvolved>()
    val tripBook: LiveData<TripInvolved> = _tripBook

    private val _destinations = MutableLiveData<Pair<PlaceModel?, PlaceModel?>>()
    val destinations: LiveData<Pair<PlaceModel?, PlaceModel?>> = _destinations


    private val _searchFilter = SingleLiveEvent<SearchTripFilter>()
    val searchFilter: LiveData<SearchTripFilter> = _searchFilter

    private val _resultTrips = MutableLiveData<MutableList<TripSearch>>()
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
        if (_searchFilter.value != null) {
            _searchFilter.value?.latitudeFrom = _destinations.value?.first?.latitude!!.toFloat()
            _searchFilter.value?.longitudeFrom = _destinations.value?.first?.longitude!!.toFloat()
            _searchFilter.value?.latitudeTo = _destinations.value?.second?.latitude!!.toFloat()
            _searchFilter.value?.longitudeTo = _destinations.value?.second?.longitude!!.toFloat()
        } else {
            _searchFilter.value = SearchTripFilter(
                _destinations.value?.first?.latitude!!.toFloat(),
                _destinations.value?.first?.longitude!!.toFloat(),
                _destinations.value?.second?.latitude!!.toFloat(),
                _destinations.value?.second?.longitude!!.toFloat()
            )
        }
    }

    fun destinationsSet() {
        destinationsIsSet.value = true
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


    fun bookTrip(tripId: String, seats: Int, pet: Boolean, userBase: UserBase, list: ArrayList<String>) {
        launchAfter(_tripBook) {
            try {
                val response = bookTripUseCase(tripId, seats, pet)

                createOrEnterConversationFirebaseUseCase.invoke(
                    userBase.id, response.creatorUser.id, tripId, response.destFrom.title + " - "
                            + response.destTo.title, "response.picture", response.creatorUser.fullName
                )

                sendMessageFirebaseUseCase.invoke(userBase.id, tripId, "", userBase.firstName, 1, list, "")
                return@launchAfter response
            } catch (e: Exception) {
                handleErrorBook(tripId)
                throw e
            }
        }
    }

    fun getTrips() {
        launchWithLiveData(true, loadResults) {
            _searchFilter.value?.let { searchFilters ->
                val response = searchTripsUseCase(searchFilters)
                delay(250)
                setSortOption(sortOption, response)
            }
        }
    }


    private fun handleErrorBook(tripId: String) {
        var tempTrips = _resultTrips.value ?: mutableListOf()
        tempTrips = tempTrips.filter { it.id != tripId }.toMutableList()
        _resultTrips.value = tempTrips
    }

    // TODO Can merge these 3 fun to 1
    private fun sortByDate(list: MutableList<TripSearch>? = null) {
        val compareList = list ?: _resultTrips.value?.toMutableList() ?: mutableListOf()
        val sortedList = compareList.sortedWith(compareBy { it.timestamp }).toMutableList()
        _resultTrips.value = sortedList
        sortOption = SortAnswerType.RELEVANT
    }

    private fun sortByPrice(list: MutableList<TripSearch>? = null) {
        val compareList = list ?: _resultTrips.value?.toMutableList() ?: mutableListOf()
        val sortedList = compareList.sortedWith(compareBy { it.price }).toMutableList()
        _resultTrips.value = sortedList
        sortOption = SortAnswerType.PRICE
    }

    private fun sortByRate(list: MutableList<TripSearch>? = null) {
        val compareList = list ?: _resultTrips.value?.toMutableList() ?: mutableListOf()
        val sortedList = compareList.sortedWith(compareBy { it.creatorUser.rate }).toMutableList()
        _resultTrips.value = sortedList
        sortOption = SortAnswerType.RATE
    }

    //Set Up Sort Option, based on previous choice
    fun setSortOption(sortOption: SortAnswerType, list: MutableList<TripSearch>? = null) {
        when (sortOption) {
            SortAnswerType.RELEVANT -> sortByDate(list)
            SortAnswerType.PRICE -> sortByPrice(list)
            else -> sortByRate(list)
        }

    }

}