package gr.fellow.fellow_traveller.ui.car

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import gr.fellow.fellow_traveller.data.base.BaseViewModel
import gr.fellow.fellow_traveller.data.base.SingleLiveEvent
import gr.fellow.fellow_traveller.domain.car.CarColor
import gr.fellow.fellow_traveller.usecase.car.AddCarToRemoteUseCase
import gr.fellow.fellow_traveller.usecase.car.GetCarColorsUseCase

class AddCarViewModel
@ViewModelInject
constructor(
    private val addCarToRemoteUseCase: AddCarToRemoteUseCase,
    private val getCarColorsUseCase: GetCarColorsUseCase,
) : BaseViewModel() {

    private val _saved = SingleLiveEvent<Boolean>()
    val saved: LiveData<Boolean> = _saved


    private val _colors = SingleLiveEvent<MutableList<CarColor>>()
    val colors: LiveData<MutableList<CarColor>> = _colors

    private val _color = SingleLiveEvent<CarColor>()
    val color: LiveData<CarColor> = _color

    fun addCar(brand: String, model: String, plate: String) {
        launch(true) {
            color.value?.let {
                addCarToRemoteUseCase(brand, model, plate, it.hex)
                _saved.value = true
            }
        }
    }


    fun setColor(carColor: CarColor) {
        _color.value = carColor
    }

    fun getColors() {
        launch {
            _colors.value = getCarColorsUseCase.invoke()
        }
    }
}