package gr.fellow.fellow_traveller.usecase.home

import gr.fellow.fellow_traveller.domain.FellowDataSource

class DeleteCarUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(carId: String): String {
        val response = dataSource.deleteCarRemote(carId)
        dataSource.deleteCarLocal(carId)

        return response
    }
}