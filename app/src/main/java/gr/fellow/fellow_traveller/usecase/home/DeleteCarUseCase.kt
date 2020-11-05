package gr.fellow.fellow_traveller.usecase.home

import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.domain.FellowDataSource

class DeleteCarUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(carId: String): ResultWrapper<String> {
        val response = dataSource.deleteCarRemote(carId)
        if (response is ResultWrapper.Success) {
            val res = dataSource.deleteCarLocal(carId)
        }
        return response
    }
}