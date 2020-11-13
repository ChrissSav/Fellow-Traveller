package gr.fellow.fellow_traveller.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.data.base.BaseViewModel
import gr.fellow.fellow_traveller.data.base.SingleLiveEvent
import gr.fellow.fellow_traveller.domain.car.Car
import gr.fellow.fellow_traveller.domain.externalError
import gr.fellow.fellow_traveller.domain.notification.Notification
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.domain.user.LocalUser
import gr.fellow.fellow_traveller.usecase.auth.ChangePasswordUseCase
import gr.fellow.fellow_traveller.usecase.auth.DeleteUserAuthLocalUseCase
import gr.fellow.fellow_traveller.usecase.home.*
import gr.fellow.fellow_traveller.usecase.notification.GetNotificationsUseCase
import gr.fellow.fellow_traveller.usecase.notification.UpdateNotificationsUseCase
import gr.fellow.fellow_traveller.usecase.register.RegisterUserLocalUseCase
import gr.fellow.fellow_traveller.usecase.trips.DeleteTripUseCase
import gr.fellow.fellow_traveller.usecase.trips.ExitFromTripUseCase
import gr.fellow.fellow_traveller.usecase.trips.GetTripsAsCreatorRemoteUseCase
import gr.fellow.fellow_traveller.usecase.trips.GetTripsAsPassengerRemoteUseCase
import gr.fellow.fellow_traveller.usecase.user.LoadUserLocalInfoUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeViewModel
@ViewModelInject
constructor(
    private val loadUserLocalInfoUseCase: LoadUserLocalInfoUseCase,
    private val deleteUserAuthLocalUseCase: DeleteUserAuthLocalUseCase,
    private val logoutRemoteUseCase: LogoutRemoteUseCase,
    private val getUserCarsRemoteUseCase: GetUserCarsRemoteUseCase,
    private val getUserCarsLocalUseCase: GetUserCarsLocalUseCase,
    private val registerCarLocalUseCase: RegisterCarLocalUseCase,
    private val deleteCarUseCase: DeleteCarUseCase,
    private val updateAccountInfoUseCase: UpdateAccountInfoUseCase,
    private val deleteUserLocalCars: DeleteUserLocalCars,
    private val registerUserLocalUseCase: RegisterUserLocalUseCase,
    private val getTripsAsCreatorRemoteUseCase: GetTripsAsCreatorRemoteUseCase,
    private val getTripsAsPassengerRemoteUseCase: GetTripsAsPassengerRemoteUseCase,
    private val updateUserPictureUseCase: UpdateUserPictureUseCase,
    private val deleteTripUseCase: DeleteTripUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val exitFromTripUseCase: ExitFromTripUseCase,
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val updateNotificationsUseCase: UpdateNotificationsUseCase
) : BaseViewModel() {


    private val _user = MutableLiveData<LocalUser>()
    val user: LiveData<LocalUser> = _user

    private val _logout = SingleLiveEvent<Boolean>()
    val logout: LiveData<Boolean> = _logout

    private val _cars = MutableLiveData<MutableList<Car>>()
    val cars: LiveData<MutableList<Car>> = _cars

    private val _addCarResult = SingleLiveEvent<Boolean>()
    val addCarResult: LiveData<Boolean> = _addCarResult

    private val _carDeletedId = SingleLiveEvent<Car>()
    val carDeletedId: LiveData<Car> = _carDeletedId

    private val _changePassword = SingleLiveEvent<Boolean>()
    val changePassword: LiveData<Boolean> = _changePassword


    /**  AS PASSENGER **/

    private var tripsAsCreatorActivePage = 0
    private val _tripsAsPassengerActive = MutableLiveData<MutableList<TripInvolved>>()
    val tripsAsPassengerActive: LiveData<MutableList<TripInvolved>> = _tripsAsPassengerActive
    val loadPassengerActive = MutableLiveData<Boolean>()

    private var tripsAsPassengerHistoryPage = 0
    private val _tripsAsPassengerHistory = MutableLiveData<MutableList<TripInvolved>>()
    val tripsAsPassengerHistory: LiveData<MutableList<TripInvolved>> = _tripsAsPassengerHistory


    /**  AS CREATOR **/

    private var tripsAsCreatorPage = 0
    private val _tripsAsCreatorActive = MutableLiveData<MutableList<TripInvolved>>()
    val tripsAsCreatorActive: LiveData<MutableList<TripInvolved>> = _tripsAsCreatorActive
    val loadCreatorActive = MutableLiveData<Boolean>()


    private var tripsAsCreatorHistoryPage = 0
    private val _tripsAsCreatorHistory = MutableLiveData<MutableList<TripInvolved>>()
    val tripsAsCreatorHistory: LiveData<MutableList<TripInvolved>> = _tripsAsCreatorHistory

    val loadHistory = MutableLiveData<Boolean>()


    /**  NOTIFICATIONS **/

    private var notificationsPage = 0
    private val _notifications = MutableLiveData<MutableList<Notification>>()
    val notifications: LiveData<MutableList<Notification>> = _notifications
    val loadNotifications = MutableLiveData<Boolean>()

    private val _notification = SingleLiveEvent<Notification>()
    val notification: LiveData<Notification> = _notification

    private val _successUpdateInfo = SingleLiveEvent<Boolean>()
    val successUpdateInfo: LiveData<Boolean> = _successUpdateInfo

    private val _successDeletion = SingleLiveEvent<String>()
    val successDeletion: LiveData<String> = _successDeletion

    /*****************************************************************************************************/

    fun loadUserInfo() {
        launch {
            if (_user.value != null) {
                return@launch
            }
            _user.value = loadUserLocalInfoUseCase()
        }
    }

    fun logOut() {
        viewModelScope.launch {
            load.value = true
            try {
                logoutRemoteUseCase()
                deleteUserAuthLocalUseCase()
                deleteUserLocalCars()
            } catch (e: java.lang.Exception) {

            }
            load.value = false
            _logout.value = true
        }
    }

    fun logOutUnauthorized() {
        viewModelScope.launch {
            try {
                deleteUserAuthLocalUseCase()
                deleteUserLocalCars()
            } catch (e: java.lang.Exception) {

            }
            _logout.value = true
        }
    }

    fun changePassword(password: String) {
        launch(true) {
            when (val response = changePasswordUseCase(password)) {
                is ResultWrapper.Success -> {
                    _changePassword.value = true
                }
                is ResultWrapper.Error -> {
                    error.value = externalError(response.error)
                }
            }
        }
    }

    /** CARS ***/


    fun loadCars() {
        launch {
            try {
                when (val response = getUserCarsRemoteUseCase()) {
                    is ResultWrapper.Success -> {
                        deleteUserLocalCars()
                        for (item in response.data) {
                            registerCarLocalUseCase(item)
                        }
                        _cars.value = getUserCarsLocalUseCase()
                    }
                    is ResultWrapper.Error -> {
                        error.value = externalError(response.error)
                    }
                }
            } catch (exception: Exception) {
                _cars.value = getUserCarsLocalUseCase()
                throw  exception
            }

        }
    }

    fun loadCarsLocal() {
        launch {
            _cars.value = getUserCarsLocalUseCase()
        }
    }


    fun deleteCar(car: Car) {
        launch(true) {
            when (val response = deleteCarUseCase(car.id)) {
                is ResultWrapper.Success -> {
                    _cars.value = getUserCarsLocalUseCase()
                    _carDeletedId.value = car
                }
                is ResultWrapper.Error -> {
                    error.value = externalError(response.error)
                }
            }
        }
    }

    /** CREATOR ***/

    fun loadTripsAsCreator(more: Boolean = false) {

        launchWithLiveData(true, loadCreatorActive) {
            if (_tripsAsCreatorActive.value != null && !more) {
                return@launchWithLiveData
            }
            delay(250)
            when (val response = getTripsAsCreatorRemoteUseCase("active", tripsAsCreatorPage)) {
                is ResultWrapper.Success -> {
                    // savedStateHandle.set(SAVED_STATE_LOCATIONS, response.data)
                    if (response.data.isNotEmpty()) {
                        tripsAsCreatorActivePage++
                    }
                    if (more)
                        _tripsAsCreatorActive.value?.addAll(response.data)
                    else
                        _tripsAsCreatorActive.value = response.data
                }
                is ResultWrapper.Error -> {
                    error.value = externalError(response.error)
                }
            }

        }
    }

    fun loadTripsAsCreatorClear() {
        launchWithLiveData(true, loadCreatorActive) {
            tripsAsCreatorActivePage = 0
            delay(250)
            when (val response = getTripsAsCreatorRemoteUseCase("active", tripsAsCreatorPage)) {
                is ResultWrapper.Success -> {
                    // savedStateHandle.set(SAVED_STATE_LOCATIONS, response.data)
                    if (response.data.isNotEmpty()) {
                        tripsAsCreatorActivePage++
                    }
                    _tripsAsCreatorActive.value = response.data

                }
                is ResultWrapper.Error -> {
                    error.value = externalError(response.error)
                }
            }
        }
    }


    fun loadTripsAsCreatorHistory(more: Boolean = false) {

        launchWithLiveData(true, loadHistory) {
            if (_tripsAsCreatorHistory.value != null && !more) {
                return@launchWithLiveData
            }
            when (val response = getTripsAsCreatorRemoteUseCase("inactive", tripsAsCreatorHistoryPage)) {
                is ResultWrapper.Success -> {
                    //savedStateHandle.set(SAVED_STATE_LOCATIONS, response.data)
                    if (response.data.isNotEmpty()) {
                        tripsAsCreatorHistoryPage++
                    }
                    if (more)
                        _tripsAsCreatorHistory.value?.addAll(response.data)
                    else
                        _tripsAsCreatorHistory.value = response.data
                }
                is ResultWrapper.Error -> {
                    error.value = externalError(response.error)
                }
            }
        }
    }

    fun loadTripsAsCreatorHistoryClear() {
        launchWithLiveData(false, loadHistory) {
            tripsAsCreatorHistoryPage = 0
            when (val response = getTripsAsCreatorRemoteUseCase("inactive", tripsAsCreatorHistoryPage)) {
                is ResultWrapper.Success -> {
                    if (response.data.isNotEmpty()) {
                        tripsAsCreatorHistoryPage++
                    }
                    _tripsAsCreatorHistory.value = response.data

                }
                is ResultWrapper.Error -> {
                    error.value = externalError(response.error)
                }
            }
        }
    }


    /** PASSENGER ***/

    fun loadTripsAsPassenger(more: Boolean = false) {
        launchWithLiveData(true, loadPassengerActive) {

            if (_tripsAsPassengerActive.value != null && !more) {
                return@launchWithLiveData
            }
            delay(250)
            when (val response = getTripsAsPassengerRemoteUseCase("active", tripsAsCreatorActivePage)) {
                is ResultWrapper.Success -> {
                    //savedStateHandle.set(SAVED_STATE_LOCATIONS, response.data)
                    if (response.data.isNotEmpty()) {
                        tripsAsCreatorActivePage++
                    }
                    if (more)
                        _tripsAsPassengerActive.value?.addAll(response.data)
                    else
                        _tripsAsPassengerActive.value = response.data
                }
                is ResultWrapper.Error -> {
                    error.value = externalError(response.error)
                }
            }
        }

    }

    fun loadTripsAsPassengerClear() {
        launchWithLiveData(true, loadPassengerActive) {
            tripsAsCreatorActivePage = 0
            delay(250)
            when (val response = getTripsAsPassengerRemoteUseCase("active", tripsAsCreatorActivePage)) {
                is ResultWrapper.Success -> {
                    //savedStateHandle.set(SAVED_STATE_LOCATIONS, response.data)
                    if (response.data.isNotEmpty()) {
                        tripsAsCreatorActivePage++
                    }
                    _tripsAsPassengerActive.value = response.data
                }
                is ResultWrapper.Error -> {
                    error.value = externalError(response.error)
                }
            }
        }
    }


    fun loadTripsAsPassengerHistory(more: Boolean = false) {

        launchWithLiveData(true, loadHistory) {
            if (_tripsAsPassengerHistory.value != null && !more) {
                return@launchWithLiveData
            }
            when (val response = getTripsAsPassengerRemoteUseCase("inactive", tripsAsPassengerHistoryPage)) {
                is ResultWrapper.Success -> {
                    //savedStateHandle.set(SAVED_STATE_LOCATIONS, response.data)
                    if (response.data.isNotEmpty()) {
                        tripsAsPassengerHistoryPage++
                    }
                    if (more)
                        _tripsAsPassengerHistory.value?.addAll(response.data)
                    else
                        _tripsAsPassengerHistory.value = response.data
                }
                is ResultWrapper.Error -> {
                    error.value = externalError(response.error)
                }
            }
        }
    }

    fun loadTripsAsPassengerHistoryClear() {
        launchWithLiveData(false, loadHistory) {
            tripsAsPassengerHistoryPage = 0
            when (val response = getTripsAsPassengerRemoteUseCase("inactive", tripsAsPassengerHistoryPage)) {
                is ResultWrapper.Success -> {
                    if (response.data.isNotEmpty()) {
                        tripsAsPassengerHistoryPage++
                    }
                    _tripsAsPassengerHistory.value = response.data

                }
                is ResultWrapper.Error -> {
                    error.value = externalError(response.error)
                }
            }
        }
    }


    /** Notification ***/

    fun loadNotifications(more: Boolean = false) {

        launchWithLiveData(true, loadNotifications) {
            if (_notifications.value != null && !more) {
                return@launchWithLiveData
            }
            when (val response = getNotificationsUseCase(notificationsPage)) {
                is ResultWrapper.Success -> {
                    //savedStateHandle.set(SAVED_STATE_LOCATIONS, response.data)
                    if (response.data.isNotEmpty()) {
                        notificationsPage++
                    }
                    if (more)
                        _notifications.value?.addAll(response.data)
                    else
                        _notifications.value = response.data
                }
                is ResultWrapper.Error -> {
                    error.value = externalError(response.error)
                }
            }
        }
    }

    fun loadNotificationsClear() {
        launchWithLiveData(false, loadNotifications) {
            notificationsPage = 0
            when (val response = getNotificationsUseCase(notificationsPage)) {
                is ResultWrapper.Success -> {
                    if (response.data.isNotEmpty()) {
                        notificationsPage++
                    }
                    _notifications.value = response.data

                }
                is ResultWrapper.Error -> {
                    error.value = externalError(response.error)
                }
            }
        }
    }


    fun readNotification(notification: Notification) {
        launchWithLiveData(false, loadNotifications) {
            when (val response = updateNotificationsUseCase(notification.id)) {
                is ResultWrapper.Success -> {
                    _notification.value = notification
                }
                is ResultWrapper.Error -> {
                    error.value = externalError(response.error)
                }
            }
        }
    }

    fun updateUserImage(picture: String?) {
        launch(true) {
            when (val response = updateUserPictureUseCase(picture)) {
                is ResultWrapper.Success -> {
                    registerUserLocalUseCase(response.data)
                    _user.value = loadUserLocalInfoUseCase()
                    decreaseUserTrips()
                }
                is ResultWrapper.Error -> {
                    error.value = externalError(response.error)
                }
            }
        }
    }

    fun updateAccountInfo(firstName: String, lastName: String, messengerLink: String?, aboutMe: String?) {
        launch(true) {
            when (val response = updateAccountInfoUseCase(firstName, lastName, messengerLink, aboutMe)) {
                is ResultWrapper.Success -> {
                    registerUserLocalUseCase(response.data)
                    _user.value = loadUserLocalInfoUseCase()
                    _successUpdateInfo.value = true
                }
                is ResultWrapper.Error -> {
                    error.value = externalError(response.error)
                }
            }
        }

    }


    fun deleteTrip(tripId: String) {
        launch(true) {
            when (val response = deleteTripUseCase(tripId)) {
                is ResultWrapper.Success -> {
                    _tripsAsCreatorActive.value = deleteTripWithId(tripId, _tripsAsCreatorActive.value)
                    decreaseUserTrips()
                    _successDeletion.value = "Επιτυχής διαγραφή ταξιδιού"
                }
                is ResultWrapper.Error -> {
                    error.value = externalError(response.error)
                }
            }
        }
    }


    private suspend fun increaseUserTrips(offers: Boolean = true) {
        if (offers)
            _user.value?.let { user ->
                registerUserLocalUseCase(
                    LocalUser(
                        user.id,
                        user.firstName,
                        user.lastName,
                        user.rate,
                        user.reviews,
                        user.picture,
                        user.aboutMe,
                        user.email,
                        user.messengerLink,
                        user.tripsOffers + 1,
                        user.tripsInvolved

                    )
                )
            }
        else
            _user.value?.let { user ->
                registerUserLocalUseCase(
                    LocalUser(
                        user.id,
                        user.firstName,
                        user.lastName,
                        user.rate,
                        user.reviews,
                        user.picture,
                        user.aboutMe,
                        user.email,
                        user.messengerLink,
                        user.tripsOffers,
                        user.tripsInvolved + 1

                    )
                )
            }
        _user.value = loadUserLocalInfoUseCase()
    }

    private suspend fun decreaseUserTrips(offers: Boolean = true) {
        if (offers)
            _user.value?.let { user ->
                registerUserLocalUseCase(
                    LocalUser(
                        user.id,
                        user.firstName,
                        user.lastName,
                        user.rate,
                        user.reviews,
                        user.picture,
                        user.aboutMe,
                        user.email,
                        user.messengerLink,
                        user.tripsOffers - 1,
                        user.tripsInvolved

                    )
                )
            }
        else
            _user.value?.let { user ->
                registerUserLocalUseCase(
                    LocalUser(
                        user.id,
                        user.firstName,
                        user.lastName,
                        user.rate,
                        user.reviews,
                        user.picture,
                        user.aboutMe,
                        user.email,
                        user.messengerLink,
                        user.tripsOffers,
                        user.tripsInvolved - 1

                    )
                )
            }
        _user.value = loadUserLocalInfoUseCase()
    }


    fun addTripCreate(trip: TripInvolved) {
        launch {
            tripsAsCreatorActive.value?.let {
                increaseUserTrips()
                val tempTrip = it
                tempTrip.add(trip)
                _tripsAsCreatorActive.value = tempTrip
            }

        }
    }

    fun addTripPassenger(trip: TripInvolved) {
        launch {
            _tripsAsPassengerActive.value?.let {
                increaseUserTrips(false)
                val tempTrip = it
                tempTrip.add(trip)
                _tripsAsPassengerActive.value = tempTrip
            }
        }
    }

    private fun deleteTripWithId(tripId: String, tripList: MutableList<TripInvolved>?): MutableList<TripInvolved> {
        val tempList = mutableListOf<TripInvolved>()
        if (tripList != null) {
            for (item in tripList) {
                if (item.id != tripId)
                    tempList.add(item)
            }
        }
        return tempList
    }


    fun exitFromTrip(tripId: String) {
        launch(true) {
            when (val response = exitFromTripUseCase(tripId)) {
                is ResultWrapper.Success -> {
                    _tripsAsPassengerActive.value = deleteTripWithId(tripId, _tripsAsPassengerActive.value)
                    decreaseUserTrips(false)
                    _successDeletion.value = "Επιτυχής αποχώρηση απο το ταξίδι"
                }
                is ResultWrapper.Error -> {
                    error.value = externalError(response.error)
                }
            }
        }
    }

}

