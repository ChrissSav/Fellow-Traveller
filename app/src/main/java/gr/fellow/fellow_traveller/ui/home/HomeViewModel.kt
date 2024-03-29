package gr.fellow.fellow_traveller.ui.home

import android.net.Uri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gr.fellow.fellow_traveller.data.base.BaseViewModel
import gr.fellow.fellow_traveller.data.base.SingleLiveEvent
import gr.fellow.fellow_traveller.domain.car.Car
import gr.fellow.fellow_traveller.domain.notification.Notification
import gr.fellow.fellow_traveller.domain.review.Review
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.domain.user.LocalUser
import gr.fellow.fellow_traveller.usecase.auth.ChangePasswordUseCase
import gr.fellow.fellow_traveller.usecase.auth.DeleteUserAuthLocalUseCase
import gr.fellow.fellow_traveller.usecase.car.DeleteCarUseCase
import gr.fellow.fellow_traveller.usecase.car.GetUserCarsRemoteUseCase
import gr.fellow.fellow_traveller.usecase.firabase.DeleteConversationFirebaseUseCase
import gr.fellow.fellow_traveller.usecase.firabase.SendMessageFirebaseUseCase
import gr.fellow.fellow_traveller.usecase.firabase.UpdateSeenStatusFirebaseUseCase
import gr.fellow.fellow_traveller.usecase.firabase.UploadPictureFirebaseUseCase
import gr.fellow.fellow_traveller.usecase.home.*
import gr.fellow.fellow_traveller.usecase.notification.GetNotificationsUseCase
import gr.fellow.fellow_traveller.usecase.notification.UpdateNotificationsUseCase
import gr.fellow.fellow_traveller.usecase.register.RegisterUserLocalUseCase
import gr.fellow.fellow_traveller.usecase.review.GetUserReviewsUseCase
import gr.fellow.fellow_traveller.usecase.trip.*
import gr.fellow.fellow_traveller.usecase.user.GetUserInfoByIdUseCase
import gr.fellow.fellow_traveller.usecase.user.LoadUserLocalInfoUseCase
import kotlinx.coroutines.delay


class HomeViewModel
@ViewModelInject
constructor(
    private val loadUserLocalInfoUseCase: LoadUserLocalInfoUseCase,
    private val deleteUserAuthLocalUseCase: DeleteUserAuthLocalUseCase,
    private val logoutRemoteUseCase: LogoutRemoteUseCase,
    private val getUserCarsRemoteUseCase: GetUserCarsRemoteUseCase,
    private val deleteCarUseCase: DeleteCarUseCase,
    private val updateAccountInfoUseCase: UpdateAccountInfoUseCase,

    private val changePasswordUseCase: ChangePasswordUseCase,
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val updateNotificationsUseCase: UpdateNotificationsUseCase,
    private val getTripInvolvedByIdUseCase: GetTripInvolvedByIdUseCase,

    //UpdateInfo
    private val getUserInfoRemoteUseCase: GetUserInfoRemoteUseCase,
    private val registerUserLocalUseCase: RegisterUserLocalUseCase,
    private val updateUserMessengerUseCase: UpdateUserMessengerUseCase,
    private val updateUserPictureUseCase: UpdateUserPictureUseCase,
    private val uploadPictureFirebaseUseCase: UploadPictureFirebaseUseCase,

    //Trip
    private val getActiveInvolvedTripsUseCase: GetActiveInvolvedTripsUseCase,
    private val getHistoryInvolvedTripsUseCase: GetHistoryInvolvedTripsUseCase,

    private val getTripsAsCreatorRemoteUseCase: GetTripsAsCreatorRemoteUseCase,
    private val getTripsAsPassengerRemoteUseCase: GetTripsAsPassengerRemoteUseCase,
    private val exitFromTripUseCase: ExitFromTripUseCase,
    private val deleteTripUseCase: DeleteTripUseCase,


    //Get Reviews
    private val getUserReviewsUseCase: GetUserReviewsUseCase,

    //Messages
    private val getUserInfoByIdUseCase: GetUserInfoByIdUseCase,
    private val sendMessageFirebaseUseCase: SendMessageFirebaseUseCase,
    private val updateSeenStatusFirebaseUseCase: UpdateSeenStatusFirebaseUseCase,
    private val deleteConversationFirebaseUseCase: DeleteConversationFirebaseUseCase
) : BaseViewModel() {

    private val DELAY_LOAD = 750L


    val currentUser: LocalUser
        get() {
            return user.value!!
        }

    val reloadConnection = MutableLiveData<Boolean>()

    private val _user = MutableLiveData<LocalUser>()
    val user: LiveData<LocalUser> = _user

    private val _logout = MutableLiveData<Boolean>()
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


    /** TRIPS **/


    /**  ACTIVE **/
    private val _tripsActive = MutableLiveData<MutableList<TripInvolved>>()
    val tripsActive: LiveData<MutableList<TripInvolved>> = _tripsActive
    val activeTripsLoader = MutableLiveData<Boolean>()


    /**  HISTORY **/
    private val _tripsHistory = MutableLiveData<MutableList<TripInvolved>>()
    val tripsHistory: LiveData<MutableList<TripInvolved>> = _tripsHistory
    val historyTripsLoader = MutableLiveData<Boolean>()


    /*****************************************************************************************************/


    fun loadUserInfo() {
        launch {
            if (_user.value != null) {
                return@launch
            }
            _user.value = loadUserLocalInfoUseCase()
            //  loadReviews()
        }
    }

    fun logOut() {
        launchWithOutException(true) {
            logoutRemoteUseCase()
            deleteUserAuthLocalUseCase()
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

    fun updateUserImage(uri: Uri?) {
        launch(true) {
            if (uri != null) {
                val url = uploadPictureFirebaseUseCase(uri, _user.value?.id!!)
                val response = updateUserPictureUseCase(url)
                registerUserLocalUseCase(response)
            } else {
                val response = updateUserPictureUseCase(null)
                registerUserLocalUseCase(response)
            }
            updateUserInfo()
        }
    }

    fun updateAccountInfo(firstName: String, lastName: String, aboutMe: String?) {
        launch(true) {
            val response = updateAccountInfoUseCase(firstName, lastName, aboutMe)
            registerUserLocalUseCase(response)
            _user.value = loadUserLocalInfoUseCase()
            delay(DELAY_LOAD)
            _successUpdateInfo.value = true
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
                delay(DELAY_LOAD)
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


    /*  fun loadTripsActive(more: Boolean = false) {
          launchWithLiveData(true, loadActiveTrips) {
              if (_tripsActive.value != null && !more) {
                  return@launchWithLiveData
              }
              val response = getTripsInvolvedUseCase("history")
              if (more)
                  delay(DELAY_LOAD)
              _tripsActive.value = response
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
                  delay(DELAY_LOAD)
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
                  delay(DELAY_LOAD)
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
                  delay(DELAY_LOAD)
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
                  delay(DELAY_LOAD)
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
                  delay(DELAY_LOAD)
              _notifications.value = response

              if (response.filter { (it.type == NotificationStatus.PASSENGER_EXIT || it.type == NotificationStatus.PASSENGER_ENTER) && !it.isRead }.any() && loadMoreTripsAsCreator) {
                  loadTripsAsCreator(true)
                  loadMoreTripsAsCreator = false
              }
              if (response.filter { it.type == NotificationStatus.TRIP_DELETED && !it.isRead }.any() && loadMoreTripsAsPassenger) {
                  loadTripsAsPassenger(true)
                  loadMoreTripsAsPassenger = false
              }

          }
      }

      fun readAllUnReadNotification() {
          launch {
              _notifications.toMutableListSafe().filter {
                  !it.isRead
              }.forEach { notification ->
                  notification.isRead = true
                  updateNotificationsUseCase(notification.id)
              }
          }
      }

      fun readNotification(notification: Notification) {
          launchWithLiveData(false, loadNotifications) {
              if (!notification.isRead) {
                  updateNotificationsUseCase(notification.id)
                  _notifications.setNotificationRead(notification.id)
              }
              _notification.value = notification
          }
      }




      fun deleteTrip(tripId: String, list: MutableList<String>) {
          launch(true) {
              deleteTripUseCase(tripId)
              _tripsAsCreatorActive.value = deleteTripWithId(tripId, _tripsAsCreatorActive.value)
              //deleteFirebaseConversation(tripId)
              sendMessageFirebaseUseCase.invoke(_user.value?.id.toString(), tripId, "", _user.value?.firstName.toString(), 4, list, "")
              getTripById(tripId)
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


      fun exitFromTrip(tripId: String, list: MutableList<String>) {
          launch(true) {
              exitFromTripUseCase(tripId)
              _tripsAsPassengerActive.value = deleteTripWithId(tripId, _tripsAsPassengerActive.value)
              deleteFirebaseConversation(tripId)
              sendMessageFirebaseUseCase.invoke(_user.value?.id.toString(), tripId, "", _user.value?.firstName.toString(), 2, list, "")
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
                      delay(DELAY_LOAD)
                  val response = getUserReviewsUseCase(it)
                  _reviews.value = response
              }
          }
      }


      private val _tripInvolvedDetails = SingleLiveEvent<TripInvolved>()
      val tripInvolvedDetails: LiveData<TripInvolved> = _tripInvolvedDetails
      private val loadTripInvolvedDetails = MutableLiveData<Boolean>()
      private val tripInvolvedDetailsDelay = 450L

      fun loadTripActiveInvolvedDetails(tripId: String, reload: Boolean = false, notificationId: String?) {
          launchWithLiveData(true, loadTripInvolvedDetails) {
              val index = _tripsActive.toMutableListSafe().indexOfFirst { it.id == tripId }
              if (reload || index == -1) {
                  if (notificationId != null) {
                      updateNotificationsUseCase(notificationId)
                      _notifications.setNotificationRead(notificationId)
                  }
                  val trip = getTripInvolvedByIdUseCase(tripId)
                  _tripsActive.addOrReplace(trip)
                  _tripInvolvedDetails.value = trip
              } else {
                  delay(tripInvolvedDetailsDelay)
                  _tripInvolvedDetails.value = _tripsActive.toMutableListSafe()[index]
              }
          }
      }

      fun loadTripCreatorHistoryInvolvedDetails(tripId: String, reload: Boolean = false, notificationId: String?) {
          launchWithLiveData(true, loadTripInvolvedDetails) {
              val index = _tripsAsCreatorHistory.toMutableListSafe().indexOfFirst { it.id == tripId }
              if (reload || index == -1) {
                  if (notificationId != null) {
                      updateNotificationsUseCase(notificationId)
                      _notifications.setNotificationRead(notificationId)
                  }
                  val trip = getTripInvolvedByIdUseCase(tripId)
                  _tripsAsCreatorHistory.addOrReplace(trip)
                  _tripInvolvedDetails.value = trip
              } else {
                  delay(tripInvolvedDetailsDelay)
                  _tripInvolvedDetails.value = _tripsAsCreatorHistory.toMutableListSafe()[index]
              }
          }
      }


      fun loadTripTakesPartActiveInvolvedDetails(tripId: String, reload: Boolean = false, notificationId: String?) {
          launchWithLiveData(true, loadTripInvolvedDetails) {
              val index = _tripsAsPassengerActive.toMutableListSafe().indexOfFirst { it.id == tripId }
              if (reload || index == -1) {
                  if (notificationId != null) {
                      updateNotificationsUseCase(notificationId)
                      _notifications.setNotificationRead(notificationId)
                  }
                  val trip = getTripInvolvedByIdUseCase(tripId)
                  _tripsAsPassengerActive.addOrReplace(trip)
                  _tripInvolvedDetails.value = trip
              } else {
                  delay(tripInvolvedDetailsDelay)
                  _tripInvolvedDetails.value = _tripsAsPassengerActive.toMutableListSafe()[index]
              }
          }
      }

      fun loadTripTakesPartHistoryInvolvedDetails(tripId: String, reload: Boolean = false, notificationId: String?) {
          launchWithLiveData(true, loadTripInvolvedDetails) {
              val index = _tripsAsPassengerHistory.toMutableListSafe().indexOfFirst { it.id == tripId }
              if (reload || index == -1) {
                  if (notificationId != null) {
                      updateNotificationsUseCase(notificationId)
                      _notifications.setNotificationRead(notificationId)
                  }
                  val trip = getTripInvolvedByIdUseCase(tripId)
                  _tripsAsPassengerHistory.addOrReplace(trip)
                  _tripInvolvedDetails.value = trip
              } else {
                  delay(tripInvolvedDetailsDelay)
                  _tripInvolvedDetails.value = _tripsAsPassengerHistory.toMutableListSafe()[index]
              }
          }
      }

      val loadMessageFirebase = MutableLiveData<Boolean>()
      fun sendFirebaseMessage(textMessage: String, tripId: String, messageType: Int, participantsList: MutableList<String>) {

          launchWithLiveData(true, loadMessageFirebase) {
              sendMessageFirebaseUseCase.invoke(
                  _user.value?.id.toString(),
                  tripId,
                  textMessage,
                  _user.value?.firstName.toString(),
                  messageType,
                  participantsList,
                  _user.value?.picture.toString()
              )
          }

      }


      fun updateSeenStatus(tripId: String) {
          launch {
              updateSeenStatusFirebaseUseCase.invoke(tripId, _user.value?.id.toString())
          }

      }

      fun deleteFirebaseConversation(tripId: String) {
          launch {
              deleteConversationFirebaseUseCase.invoke(_user.value?.id.toString(), tripId)
          }

      }

      private val _conversationList = MutableLiveData<MutableList<Conversation>>()
      val conversationList: LiveData<MutableList<Conversation>> = _conversationList
      val loadConversations = MutableLiveData<Boolean>()

      fun loadConversations(list: MutableList<Conversation>) {
          launchWithLiveData(true, loadConversations) {
              delay(DELAY_LOAD)
              list.sortByDescending { it.timestamp }
              _conversationList.value = list

          }
      }

      private val _messagesList = SingleLiveEvent<ChatMessage>()
      val messagesList: LiveData<ChatMessage> = _messagesList
      val loadMessages = MutableLiveData<Boolean>()

      fun loadChatMessage(chatMessage: ChatMessage) {

          _messagesList.value = chatMessage
      }

      private val _listOfParticipants = SingleLiveEvent<MutableList<UserInfo>>()
      val listOfParticipants: LiveData<MutableList<UserInfo>> = _listOfParticipants

      fun getParticipants(participantsIDs: MutableList<String>) {

          launch {
              val tempList = mutableListOf<UserInfo>()
              participantsIDs.forEach {
                  tempList.add(getUserInfoByIdUseCase.invoke(it))
              }

              _listOfParticipants.value = tempList
          }

      }

      private val _tripInfo = SingleLiveEvent<TripInvolved?>()
      val tripInfo: LiveData<TripInvolved?> = _tripInfo

      fun getTripById(tripId: String) {
          launch {
              try {
                  val tripInvolved = getTripInvolvedByIdUseCase.invoke(tripId)
                  _tripInfo.value = tripInvolved
              } catch (e: BaseApiException) {
                  _tripInfo.value = null
              }

          }

      }*/


    /*********************************NEW******************************************************************/
    //getActiveInvolvedTripsUseCase:
    //getHistoryInvolvedTripsUseCase:

    /** TRIPS ***/

    fun loadActiveTrips(forceLoad: Boolean = false) {
        // launchWithLiveData(true, loadActiveTrips) {
        launchWithLiveData(true, activeTripsLoader) {
            if (_tripsActive.value != null && !forceLoad) {
                return@launchWithLiveData
            }
//            val response =
//            if (more)
//                delay(DELAY_LOAD)
            val trips = getActiveInvolvedTripsUseCase()
            if (forceLoad)
                delay(DELAY_LOAD)
            _tripsActive.value = trips
        }
    }


    fun loadHistoryTrips(forceLoad: Boolean = false) {
        // launchWithLiveData(true, loadActiveTrips) {
        launchWithLiveData(true, historyTripsLoader) {
            if (_tripsHistory.value != null && !forceLoad) {
                return@launchWithLiveData
            }
//            val response =
            val trips = getHistoryInvolvedTripsUseCase()
            if (forceLoad)
                delay(DELAY_LOAD)
            _tripsHistory.value = trips
        }
    }
}




