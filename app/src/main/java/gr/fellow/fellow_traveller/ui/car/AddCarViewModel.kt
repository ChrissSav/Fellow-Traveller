package gr.fellow.fellow_traveller.ui.car

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.ui.help.BaseViewModel
import gr.fellow.fellow_traveller.ui.help.SingleLiveEvent
import gr.fellow.fellow_traveller.usecase.home.AddCarToRemoteUseCase
import gr.fellow.fellow_traveller.usecase.home.RegisterCarLocalUseCase

class AddCarViewModel
@ViewModelInject
constructor(
    private val registerCarLocalUseCase: RegisterCarLocalUseCase,
    private val addCarToRemoteUseCase: AddCarToRemoteUseCase
) : BaseViewModel() {

    private val _saved = SingleLiveEvent<Boolean>()
    val saved: LiveData<Boolean> = _saved

    fun addCar(brand: String, model: String, plate: String, color: String) {
        launch {
            when (val response = addCarToRemoteUseCase(brand, model, plate, color)) {
                is ResultWrapper.Success -> {
                    registerCarLocalUseCase(response.data)
                    _saved.value = true
                }
                is ResultWrapper.Error -> {
                    error.value = response.error.msg
                }
            }
        }
    }
}