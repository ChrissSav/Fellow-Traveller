package gr.fellow.fellow_traveller.usecase.home

import android.content.Context
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.framework.network.fellow.request.CarRequest
import gr.fellow.fellow_traveller.framework.network.fellow.response.CarResponse

class AddCarToRemoteUseCase(
    private val dataSource: FellowDataSource,
    private val context: Context
) {

    suspend operator fun invoke(brand: String, model: String, plate: String, color: String): ResultWrapper<CarResponse> {
        val response = dataSource.addCarRemote(CarRequest(brand, model, plate, color))
        if (response is ResultWrapper.Error) {
            when (response.error.code) {
                300 ->
                    response.error.msg =
                        context.resources.getString(R.string.ERROR_PLATE_ALREADY_EXISTS)
                else ->
                    response.error.msg =
                        context.resources.getString(R.string.ERROR_API_UNREACHABLE)
            }
        }
        return response
    }

}