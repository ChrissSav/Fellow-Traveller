package gr.fellow.fellow_traveller.usecase.car

import gr.fellow.fellow_traveller.domain.FellowDataSource

class DeleteCarUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(carId: String): String {
        return dataSource.deleteCarRemote(carId)
    }
}