package gr.fellow.fellow_traveller.usecase.home

import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.framework.network.fellow.response.StatusHandleResponse

class DeleteCarUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(carId: Int): ResultWrapper<StatusHandleResponse> {
        val response = dataSource.deleteCarRemote(carId)
        if (response is ResultWrapper.Success) {
            val res = dataSource.deleteCar(carId)
        }
        return response
    }
}