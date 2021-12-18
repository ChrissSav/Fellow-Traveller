package gr.fellow.fellow_traveller.ui.home.trip_details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gr.fellow.fellow_traveller.data.base.BaseViewModel
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.domain.user.LocalUser
import gr.fellow.fellow_traveller.usecase.user.LoadUserLocalInfoUseCase


class TripDetailsViewModel
@ViewModelInject
constructor(
    private val loadUserLocalInfoUseCase: LoadUserLocalInfoUseCase
) : BaseViewModel() {


    private val _tripUser = MutableLiveData<Pair<TripInvolved, LocalUser>>()
    val tripUser: LiveData<Pair<TripInvolved, LocalUser>> = _tripUser


    fun setTrip(currentTrip: TripInvolved) {
        launch {
            _tripUser.value = Pair(currentTrip, loadUserLocalInfoUseCase())
        }
    }


}




