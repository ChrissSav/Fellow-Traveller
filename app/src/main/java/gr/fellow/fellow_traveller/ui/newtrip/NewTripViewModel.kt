package gr.fellow.fellow_traveller.ui.newtrip

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gr.fellow.fellow_traveller.data.ResultWrapperSecond
import gr.fellow.fellow_traveller.data.base.BaseViewModel
import gr.fellow.fellow_traveller.domain.BagsStatusType
import gr.fellow.fellow_traveller.domain.car.Car
import gr.fellow.fellow_traveller.domain.externalError
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.domain.user.LocalUser
import gr.fellow.fellow_traveller.framework.network.google.model.DestinationModel
import gr.fellow.fellow_traveller.usecase.LoadUserLocalInfoUseCase
import gr.fellow.fellow_traveller.usecase.home.GetUserCarsLocalUseCase
import gr.fellow.fellow_traveller.usecase.newtrip.RegisterTripRemoteUseCase
import gr.fellow.fellow_traveller.utils.dateTimeToTimestamp


class NewTripViewModel
@ViewModelInject
constructor(
    private val loadUserLocalInfoUseCase: LoadUserLocalInfoUseCase,
    private val getUserCarsLocalUseCase: GetUserCarsLocalUseCase,
    private val registerTripRemoteUseCase: RegisterTripRemoteUseCase
) : BaseViewModel() {


    private val _success = MutableLiveData<TripInvolved>()
    val success: LiveData<TripInvolved> = _success

    private val _destinationFrom = MutableLiveData<DestinationModel>()
    val destinationFrom: LiveData<DestinationModel> = _destinationFrom

    private val _destinationTo = MutableLiveData<DestinationModel>()
    val destinationTo: LiveData<DestinationModel> = _destinationTo

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


    fun applyDate(date: String) {
        launchSecond {
            _date.value = date
        }

    }

    fun applyTime(time: String) {
        launchSecond {
            _time.value = time
        }

    }


    fun setDestinationFrom(id: String, title: String) {
        launchSecond {
            _destinationFrom.value = DestinationModel(id, title)
        }
    }

    fun setDestinationTo(id: String, title: String) {
        launchSecond {
            _userInfo.value = loadUserLocalInfoUseCase()
            _destinationTo.value = DestinationModel(id, title)
        }
    }


    fun setPrice(priceCurrent: Float) {
        launchSecond {
            _price.value = priceCurrent
        }
    }

    fun setSeats(seatCurrent: Int) {
        launchSecond {
            _seats.value = seatCurrent
        }
    }

    fun setBags(bagsCurrent: BagsStatusType) {
        launchSecond {
            _bags.value = bagsCurrent
        }
    }

    fun setPet(pet: Boolean) {
        launchSecond {
            _pet.value = pet
        }
    }

    fun setMessage(msg: String?) {
        _message.value = msg?.trim()
    }

    fun setCar(carTemp: Car) {
        launchSecond {
            _car.value = carTemp
        }
    }

    fun loadUserCars() {
        launchSecond {
            _carList.value = getUserCarsLocalUseCase()
        }
    }


    fun registerTrip() {
        launchSecond(true) {
            when (val response = registerTripRemoteUseCase(
                destinationFrom.value?.placeId.toString(), destinationTo.value?.placeId.toString(), car.value?.id.toString(),
                pet.value!!, seats.value!!, bags.value!!.value, message.value!!, price.value!!, getTimestamp()
            )
                ) {
                is ResultWrapperSecond.Success -> {
                    _success.value = response.data
                }

                is ResultWrapperSecond.Error -> {
                    errorSecond.value = externalError(response.error)
                }
            }

        }
    }


    fun getTimestamp(): Long {
        return dateTimeToTimestamp(date.value.toString(), time.value.toString())
    }
}