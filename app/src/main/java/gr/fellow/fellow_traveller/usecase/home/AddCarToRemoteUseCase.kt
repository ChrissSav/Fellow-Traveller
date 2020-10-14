package gr.fellow.fellow_traveller.usecase.home

import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.domain.car.Car
import gr.fellow.fellow_traveller.framework.network.fellow.request.CarRequest

class AddCarToRemoteUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(brand: String, model: String, plate: String, color: String): ResultWrapper<Car> {
        val response = dataSource.addCarRemote(CarRequest(brand, model, plate, color))
        if (response is ResultWrapper.Error) {
            when (response.error.code) {
                300 ->
                    response.error.msg = R.string.ERROR_PLATE_ALREADY_EXISTS
                else ->
                    response.error.msg = R.string.ERROR_API_UNREACHABLE
            }
        }
        return response
    }

}