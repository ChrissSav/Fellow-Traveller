package gr.fellow.fellow_traveller.usecase.home

import gr.fellow.fellow_traveller.data.ResultWrapperSecond
import gr.fellow.fellow_traveller.domain.FellowDataSource

class DeleteCarUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(carId: String): ResultWrapperSecond<String> {
        val response = dataSource.deleteCarRemote(carId)
        if (response is ResultWrapperSecond.Success) {
            val res = dataSource.deleteCarLocal(carId)
        }
        return response
    }
}