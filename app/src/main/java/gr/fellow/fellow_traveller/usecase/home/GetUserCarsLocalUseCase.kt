package gr.fellow.fellow_traveller.usecase.home

import gr.fellow.fellow_traveller.domain.Car
import gr.fellow.fellow_traveller.domain.FellowDataSource

class GetUserCarsLocalUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(): MutableList<Car> =
        dataSource.getAllCarsLocal()
}