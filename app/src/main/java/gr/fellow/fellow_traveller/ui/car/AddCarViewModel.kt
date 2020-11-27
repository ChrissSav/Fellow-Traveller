package gr.fellow.fellow_traveller.ui.car

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import gr.fellow.fellow_traveller.data.base.BaseViewModel
import gr.fellow.fellow_traveller.data.base.SingleLiveEvent
import gr.fellow.fellow_traveller.usecase.home.AddCarToRemoteUseCase

class AddCarViewModel
@ViewModelInject
constructor(
    private val addCarToRemoteUseCase: AddCarToRemoteUseCase
) : BaseViewModel() {

    private val _saved = SingleLiveEvent<Boolean>()
    val saved: LiveData<Boolean> = _saved

    fun addCar(brand: String, model: String, plate: String, color: String) {
        launch(true) {
            addCarToRemoteUseCase(brand, model, plate, color)
            _saved.value = true
        }
    }
}