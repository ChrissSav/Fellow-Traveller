package gr.fellow.fellow_traveller.ui.car

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.domain.mappers.toCarEntity
import gr.fellow.fellow_traveller.ui.help.BaseViewModel
import gr.fellow.fellow_traveller.usecase.home.AddCarToRemoteUseCase
import gr.fellow.fellow_traveller.usecase.home.RegisterCarLocalUseCase

class AddCarViewModel
@ViewModelInject
constructor(
    private val context: Context,
    private val registerCarLocalUseCase: RegisterCarLocalUseCase,
    private val addCarToRemoteUseCase: AddCarToRemoteUseCase
) : BaseViewModel(context) {

    private val _saved = MutableLiveData<Boolean>()
    val saved: LiveData<Boolean> = _saved

    fun addCar(brand: String, model: String, plate: String, color: String) {
        launch {
            when (val response = addCarToRemoteUseCase(brand, model, plate, color)) {
                is ResultWrapper.Success -> {
                    registerCarLocalUseCase(response.data.toCarEntity())
                    _saved.value = true
                }
                is ResultWrapper.Error -> {
                    _error.value = response.error.msg
                }
            }
        }
    }
}