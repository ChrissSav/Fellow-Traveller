package gr.fellow.fellow_traveller.ui.home

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.base.BaseViewModel
import gr.fellow.fellow_traveller.data.base.SingleLiveEvent
import gr.fellow.fellow_traveller.domain.car.Car
import gr.fellow.fellow_traveller.domain.notification.Notification
import gr.fellow.fellow_traveller.domain.review.Review
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.domain.user.LocalUser
import gr.fellow.fellow_traveller.service.NotificationService
import gr.fellow.fellow_traveller.usecase.auth.ChangePasswordUseCase
import gr.fellow.fellow_traveller.usecase.auth.DeleteUserAuthLocalUseCase
import gr.fellow.fellow_traveller.usecase.home.*
import gr.fellow.fellow_traveller.usecase.notification.GetNotificationsUseCase
import gr.fellow.fellow_traveller.usecase.notification.UpdateNotificationsUseCase
import gr.fellow.fellow_traveller.usecase.register.RegisterUserLocalUseCase
import gr.fellow.fellow_traveller.usecase.review.GetUserReviewsUseCase
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
    private val deleteCarUseCase: DeleteCarUseCase,
    private val updateAccountInfoUseCase: UpdateAccountInfoUseCase,
    private val getTripsAsCreatorRemoteUseCase: GetTripsAsCreatorRemoteUseCase,
    private val getTripsAsPassengerRemoteUseCase: GetTripsAsPassengerRemoteUseCase,
    private val updateUserPictureUseCase: UpdateUserPictureUseCase,
    private val deleteTripUseCase: DeleteTripUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val exitFromTripUseCase: ExitFromTripUseCase,
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val updateNotificationsUseCase: UpdateNotificationsUseCase,
    //UpdateInfo
    private val getUserInfoRemoteUseCase: GetUserInfoRemoteUseCase,
    private val registerUserLocalUseCase: RegisterUserLocalUseCase,
    private val updateUserMessengerUseCase: UpdateUserMessengerUseCase,
    //Get Reviews
    private val getUserReviewsUseCase: GetUserReviewsUseCase
) : BaseViewModel() {

    val reloadConnection = MutableLiveData<Boolean>()

    private val _user = MutableLiveData<LocalUser>()
    val user: LiveData<LocalUser> = _user

    private val _logout = SingleLiveEvent<Boolean>()
    val logout: LiveData<Boolean> = _logout

    private val _cars = MutableLiveData<MutableList<Car>>()
    val cars: LiveData<MutableList<Car>> = _cars
    val loadCars = MutableLiveData<Boolean>()

    private val _messengerUpdate = SingleLiveEvent<Boolean>()
    val messengerUpdate: LiveData<Boolean> = _messengerUpdate


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

    private val _notifications = MutableLiveData<MutableList<Notification>>()
    val notifications: LiveData<MutableList<Notification>> = _notifications
    val loadNotifications = MutableLiveData<Boolean>()

    private val _notification = SingleLiveEvent<Notification>()
    val notification: LiveData<Notification> = _notification

    private val _successUpdateInfo = SingleLiveEvent<Boolean>()
    val successUpdateInfo: LiveData<Boolean> = _successUpdateInfo

    private val _successDeletion = SingleLiveEvent<Int>()
    val successDeletion: LiveData<Int> = _successDeletion

    /** Profile **/
    private val _reviews = MutableLiveData<MutableList<Review>>()
    val reviews: LiveData<MutableList<Review>> = _reviews
    val loadReviews = MutableLiveData<Boolean>()

    /*****************************************************************************************************/

    fun loadUserInfo() {
        launch {
            if (_user.value != null) {
                return@launch
            }
            _user.value = loadUserLocalInfoUseCase()
            loadReviews()
        }
    }

    fun logOut() {
        viewModelScope.launch {
            load.value = true
            try {
                logoutRemoteUseCase()
                deleteUserAuthLocalUseCase()
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
            _messengerUpdate.value = true
        }
    }


    /** CARS ***/


    fun loadCars(more: Boolean = false) {
        launchWithLiveData(true, loadCars) {
            if (_tripsAsCreatorActive.value != null && !more) {
                return@launchWithLiveData
            }
            val response = getUserCarsRemoteUseCase()
            if (more)
                delay(350)
            _cars.value = response
        }
    }

    fun deleteCar(car: Car) {
        launch(true) {
            deleteCarUseCase(car.id)
            _cars.value = getUserCarsRemoteUseCase()
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
            if (more)
                delay(350)
            _tripsAsCreatorActive.value = response
        }
    }


    fun loadTripsAsCreatorHistory(more: Boolean = false) {

        launchWithLiveData(true, loadHistory) {
            if (_tripsAsCreatorHistory.value != null && !more) {
                return@launchWithLiveData
            }
            val response = getTripsAsCreatorRemoteUseCase("inactive")
            if (more)
                delay(350)
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
            if (more)
                delay(350)
            _tripsAsPassengerActive.value = response
        }

    }


    fun loadTripsAsPassengerHistory(more: Boolean = false) {
        launchWithLiveData(true, loadHistory) {
            if (_tripsAsPassengerHistory.value != null && !more) {
                return@launchWithLiveData
            }
            val response = getTripsAsPassengerRemoteUseCase("inactive")
            if (more)
                delay(350)
            _tripsAsPassengerHistory.value = response
        }
    }


    fun reload(l: Boolean = false) {
        launch {
            reloadConnection.value = l
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
            val response = getNotificationsUseCase()
            if (more)
                delay(350)
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
            deleteTripUseCase(tripId)
            _tripsAsCreatorActive.value = deleteTripWithId(tripId, _tripsAsCreatorActive.value)
            _successDeletion.value = R.string.delete_trip_success
            updateUserInfo()
        }
    }


    fun addTripCreate(trip: TripInvolved) {
        launch {
            _tripsAsCreatorActive.value?.let { list ->
                updateUserInfo()
                val tempTrip = list
                tempTrip.add(trip)
                _tripsAsCreatorActive.value = tempTrip
                try {
                    val car = _cars.value?.first { it.plate == trip.car.plate }
                    if (car == null)
                        loadCars(true)
                } catch (e: NoSuchElementException) {
                    loadCars(true)
                }
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
            exitFromTripUseCase(tripId)
            _tripsAsPassengerActive.value = deleteTripWithId(tripId, _tripsAsPassengerActive.value)
            _successDeletion.value = R.string.leave_trip_success
            updateUserInfo()
        }
    }

    fun loadReviews(more: Boolean = false) {
        launchWithLiveData(true, loadReviews) {
            _user.value?.id?.let {
                if (_reviews.value != null && !more) {
                    return@launchWithLiveData
                }
                if (more)
                    delay(350)
                val response = getUserReviewsUseCase(it)
                _reviews.value = response
            }
        }
    }


    private val TAG = "MyService"

    private val _mIsProgressBarUpdating = MutableLiveData<Boolean>()
    val mIsProgressBarUpdating: LiveData<Boolean> = _mIsProgressBarUpdating

    private val _mBinder = MutableLiveData<NotificationService.MyBinder>()
    val mBinder: LiveData<NotificationService.MyBinder> = _mBinder


    var serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, iBinder: IBinder) {
            Log.d(TAG, "ServiceConnection: connected to service.")
            // We've bound to MyService, cast the IBinder and get MyBinder instance
            val binder = iBinder as NotificationService.MyBinder
            _mBinder.postValue(binder)
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            Log.d(TAG, "ServiceConnection: disconnected from service.")
            _mBinder.postValue(null)
        }
    }

    fun setIsProgressBarUpdating(isUpdating: Boolean) {
        _mIsProgressBarUpdating.value = (isUpdating)
    }

}

