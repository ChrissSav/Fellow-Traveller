package gr.fellow.fellow_traveller.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import gr.fellow.fellow_traveller.data.base.BaseViewModel
import gr.fellow.fellow_traveller.data.base.SingleLiveEvent
import gr.fellow.fellow_traveller.domain.car.Car
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
    private val getTripsAsCreatorRemoteUseCase: GetTripsAsCreatorRemoteUseCase,
    private val getTripsAsPassengerRemoteUseCase: GetTripsAsPassengerRemoteUseCase,
    private val updateUserPictureUseCase: UpdateUserPictureUseCase,
    private val deleteTripUseCase: DeleteTripUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val exitFromTripUseCase: ExitFromTripUseCase,
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val updateNotificationsUseCase: UpdateNotificationsUseCase,
    // UpdateInfo
    private val getUserInfoRemoteUseCase: GetUserInfoRemoteUseCase,
    private val registerUserLocalUseCase: RegisterUserLocalUseCase,
    private val updateUserMessengerUseCase: UpdateUserMessengerUseCase
) : BaseViewModel() {


    private val _user = MutableLiveData<LocalUser>()
    val user: LiveData<LocalUser> = _user

    private val _logout = SingleLiveEvent<Boolean>()
    val logout: LiveData<Boolean> = _logout

    private val _cars = MutableLiveData<MutableList<Car>>()
    val cars: LiveData<MutableList<Car>> = _cars


    private val _carDeletedId = SingleLiveEvent<Car>()
    val carDeletedId: LiveData<Car> = _carDeletedId

    private val _changePassword = SingleLiveEvent<Boolean>()
    val changePassword: LiveData<Boolean> = _changePassword


    /**  AS PASSENGER **/

    private val _tripsAsPassengerActive = MutableLiveData<MutableList<TripInvolved>>()
    val tripsAsPassengerActive: LiveData<MutableList<TripInvolved>> = _tripsAsPassengerActive
    val loadPassengerActive = MutableLiveData<Boolean>()
    private var loadMoreTripsAsPassenger = true


    private val _tripsAsPassengerHistory = MutableLiveData<MutableList<TripInvolved>>()
    val tripsAsPassengerHistory: LiveData<MutableList<TripInvolved>> = _tripsAsPassengerHistory


    /**  AS CREATOR **/

    private val _tripsAsCreatorActive = MutableLiveData<MutableList<TripInvolved>>()
    val tripsAsCreatorActive: LiveData<MutableList<TripInvolved>> = _tripsAsCreatorActive
    val loadCreatorActive = MutableLiveData<Boolean>()
    private var loadMoreTripsAsCreator = true


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


    private fun updateUserInfo() {
        launch {
            val response = getUserInfoRemoteUseCase()
            registerUserLocalUseCase(response)
            _user.value = loadUserLocalInfoUseCase()
        }
    }


    fun changePassword(prevPassword: String, password: String) {
        launch(true) {
            changePasswordUseCase(prevPassword, password)
            _changePassword.value = true
        }
    }

    fun changeMessenger(messenger: String) {
        launch(true) {
            updateUserMessengerUseCase(messenger)
            updateUserInfo()
        }
    }


    /** CARS ***/


    fun loadCars() {
        launch {
            try {
                val response = getUserCarsRemoteUseCase()
                deleteUserLocalCars()
                for (item in response) {
                    registerCarLocalUseCase(item)
                }
                _cars.value = getUserCarsLocalUseCase()

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
            val response = deleteCarUseCase(car.id)
            _cars.value = getUserCarsLocalUseCase()
            _carDeletedId.value = car
        }
    }

    /** CREATOR ***/

    fun loadTripsAsCreator(more: Boolean = false) {

        launchWithLiveData(true, loadCreatorActive) {
            if (_tripsAsCreatorActive.value != null && !more) {
                return@launchWithLiveData
            }
            val response = getTripsAsCreatorRemoteUseCase("active")
            _tripsAsCreatorActive.value = response
        }
    }


    fun loadTripsAsCreatorHistory(more: Boolean = false) {

        launchWithLiveData(true, loadHistory) {
            if (_tripsAsCreatorHistory.value != null && !more) {
                return@launchWithLiveData
            }
            val response = getTripsAsCreatorRemoteUseCase("inactive")
            _tripsAsCreatorHistory.value = response
        }
    }


    /** PASSENGER ***/

    fun loadTripsAsPassenger(more: Boolean = false) {
        launchWithLiveData(true, loadPassengerActive) {

            if (_tripsAsPassengerActive.value != null && !more) {
                return@launchWithLiveData
            }
            val response = getTripsAsPassengerRemoteUseCase("active")
            _tripsAsPassengerActive.value = response
        }

    }


    fun loadTripsAsPassengerHistory(more: Boolean = false) {
        launchWithLiveData(true, loadHistory) {
            if (_tripsAsPassengerHistory.value != null && !more) {
                return@launchWithLiveData
            }
            val response = getTripsAsPassengerRemoteUseCase("inactive")
            _tripsAsPassengerHistory.value = response
        }
    }


    /** Notification ***/

    fun loadNotifications(more: Boolean = false) {

        launchWithLiveData(true, loadNotifications) {
            if (_notifications.value != null && !more) {
                return@launchWithLiveData
            }
            loadMoreTripsAsCreator = true
            loadMoreTripsAsPassenger = true
            val response = getNotificationsUseCase(notificationsPage)
            if (response.isNotEmpty()) {
                notificationsPage++
            }
            if (more)
                _notifications.value?.addAll(response)
            else
                _notifications.value = response

            if (response.filter { (it.type == 1 || it.type == 2) && !it.isRead }.any() && loadMoreTripsAsCreator) {
                loadTripsAsCreator(true)
                loadMoreTripsAsCreator = false
            }
            if (response.filter { it.type == 3 && !it.isRead }.any() && loadMoreTripsAsPassenger) {
                loadTripsAsPassenger(true)
                loadMoreTripsAsPassenger = false
            }

        }
    }

    fun loadNotificationsClear() {
        launchWithLiveData(false, loadNotifications) {
            notificationsPage = 0
            val response = getNotificationsUseCase(notificationsPage)
            if (response.isNotEmpty()) {
                notificationsPage++
            }
            _notifications.value = response
        }
    }


    fun readNotification(notification: Notification) {
        launchWithLiveData(false, loadNotifications) {
            if (!notification.isRead) {
                updateNotificationsUseCase(notification.id)
                notification.isRead = true
                val temp = _notifications.value ?: mutableListOf()
                val index = temp.indexOf(notification)
                temp[index] = notification
                _notifications.value = temp
            }
            _notification.value = notification
        }
    }

    fun updateUserImage(picture: String?) {
        launch(true) {
            val response = updateUserPictureUseCase(picture)
            registerUserLocalUseCase(response)
            _user.value = loadUserLocalInfoUseCase()
            updateUserInfo()
        }
    }

    fun updateAccountInfo(firstName: String, lastName: String, aboutMe: String?) {
        launch(true) {
            val response = updateAccountInfoUseCase(firstName, lastName, aboutMe)
            registerUserLocalUseCase(response)
            _user.value = loadUserLocalInfoUseCase()
            _successUpdateInfo.value = true
        }

    }


    fun deleteTrip(tripId: String) {
        launch(true) {
            val response = deleteTripUseCase(tripId)
            _tripsAsCreatorActive.value = deleteTripWithId(tripId, _tripsAsCreatorActive.value)
            _successDeletion.value = "Επιτυχής διαγραφή ταξιδιού"
            updateUserInfo()
        }
    }


    fun addTripCreate(trip: TripInvolved) {
        launch {
            tripsAsCreatorActive.value?.let {
                updateUserInfo()
                val tempTrip = it
                tempTrip.add(trip)
                _tripsAsCreatorActive.value = tempTrip
            }

        }
    }

    fun addTripPassenger(trip: TripInvolved) {
        launch {
            _tripsAsPassengerActive.value?.let {
                updateUserInfo()
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
            val response = exitFromTripUseCase(tripId)
            _tripsAsPassengerActive.value = deleteTripWithId(tripId, _tripsAsPassengerActive.value)
            _successDeletion.value = "Επιτυχής αποχώρηση απο το ταξίδι"
            updateUserInfo()
        }
    }

}

