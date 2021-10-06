package gr.fellow.fellow_traveller.ui.newtrip

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gr.fellow.fellow_traveller.data.base.BaseViewModel
import gr.fellow.fellow_traveller.domain.BagsStatusType
import gr.fellow.fellow_traveller.domain.car.Car
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.domain.user.LocalUser
import gr.fellow.fellow_traveller.domain.user.UserBase
import gr.fellow.fellow_traveller.framework.network.google.model.DestinationModel
import gr.fellow.fellow_traveller.usecase.firabase.CreateOrEnterConversationFirebaseUseCase
import gr.fellow.fellow_traveller.usecase.firabase.SendMessageFirebaseUseCase
import gr.fellow.fellow_traveller.usecase.home.GetUserCarsRemoteUseCase
import gr.fellow.fellow_traveller.usecase.newtrip.RegisterTripRemoteUseCase
import gr.fellow.fellow_traveller.usecase.user.LoadUserLocalInfoUseCase
import gr.fellow.fellow_traveller.utils.dateTimeToTimestamp


class NewTripViewModel
@ViewModelInject
constructor(
    private val loadUserLocalInfoUseCase: LoadUserLocalInfoUseCase,
    private val getUserCarsRemoteUseCase: GetUserCarsRemoteUseCase,
    private val registerTripRemoteUseCase: RegisterTripRemoteUseCase,
    private val createOrEnterConversationFirebaseUseCase: CreateOrEnterConversationFirebaseUseCase,
    private val sendMessageFirebaseUseCase: SendMessageFirebaseUseCase,

    ) : BaseViewModel() {


    private val _success = MutableLiveData<TripInvolved>()
    val success: LiveData<TripInvolved> = _success

    private val _destinationFrom = MutableLiveData<DestinationModel>()
    val destinationFrom: LiveData<DestinationModel> = _destinationFrom

    private val _destinationTo = MutableLiveData<DestinationModel>()
    val destinationTo: LiveData<DestinationModel> = _destinationTo


    private val _destinationPickUp = MutableLiveData<DestinationModel>()
    val destinationPickUp: LiveData<DestinationModel> = _destinationPickUp

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    private val _time = MutableLiveData<String>()
    val time: LiveData<String> = _time

    private val _car = MutableLiveData<Car>()
    val car: LiveData<Car> = _car

    private val _carList = MutableLiveData<MutableList<Car>>()
    val carList: LiveData<MutableList<Car>> = _carList

    private val _seats = MutableLiveData<Int>()
    val seats: LiveData<Int> = _seats

    private val _bags = MutableLiveData<BagsStatusType>()
    val bags: LiveData<BagsStatusType> = _bags

    private val _pet = MutableLiveData<Boolean>()
    val pet: LiveData<Boolean> = _pet

    private val _price = MutableLiveData<Float>()
    val price: LiveData<Float> = _price

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _userInfo = MutableLiveData<LocalUser>()
    val userInfo: LiveData<LocalUser> = _userInfo


    init {
        _seats.value = 1
        _pet.value = false
    }

    fun applyDate(date: String) {
        _date.value = date
    }

    fun applyTime(time: String) {
        _time.value = time
    }


    fun setDestinationFrom(id: String, title: String) {
        _destinationFrom.value = DestinationModel(id, title)
    }

    fun setDestinationPickUp(id: String, title: String) {
        _destinationPickUp.value = DestinationModel(id, title)
    }

    fun setDestinationTo(id: String, title: String) {
        launch {
            _userInfo.value = loadUserLocalInfoUseCase()
            _destinationTo.value = DestinationModel(id, title)
        }
    }


    fun setPrice(priceCurrent: Float) {
        if (priceCurrent < 0)
            _price.value = priceCurrent * -1
        else
            _price.value = priceCurrent
    }

    fun setSeats(seatCurrent: Int) {
        _seats.value = seatCurrent
    }

    fun setBags(bagsCurrent: BagsStatusType) {
        _bags.value = bagsCurrent
    }

    fun setPet(pet: Boolean) {
        _pet.value = pet
    }

    fun setMessage(msg: String?) {
        _message.value = msg?.trim()
    }

    fun setCar(carTemp: Car) {
        _car.value = carTemp
    }

    fun loadUserCars() {
        launch {
            _carList.value = getUserCarsRemoteUseCase()
        }
    }


    fun registerTrip(userBase: UserBase) {
        launchAfter(_success) {


            var msg: String? = null
            if (message.value?.trim()?.length!! > 0) {
                msg = message.value.toString()
            }

            val response = registerTripRemoteUseCase(
                destinationFrom.value?.placeId.toString(), destinationTo.value?.placeId.toString(), car.value?.id.toString(),
                pet.value!!, seats.value!!, bags.value!!.code, msg, price.value!!, getTimestamp()
            )

            //We create only the creator's conversation
            createOrEnterConversationFirebaseUseCase.invoke(
                userBase.id, userBase.id, response.id, response.destFrom.title + " - "
                        + response.destTo.title, response.picture, response.creatorUser.fullName
            )

            //We create a temp list with id, in this case we add only creator id and then we send a message with type 3(First Messag)
            var tempIdList = mutableListOf<String>()
            tempIdList.add(response.creatorUser.id)
            sendMessageFirebaseUseCase.invoke(userBase.id, response.id, "", "", 3, tempIdList, response.picture)

            return@launchAfter response

        }
    }


    fun getTimestamp(): Long {
        return dateTimeToTimestamp(date.value.toString(), time.value.toString())
    }
}