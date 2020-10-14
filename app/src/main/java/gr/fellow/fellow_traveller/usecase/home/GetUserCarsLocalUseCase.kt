package gr.fellow.fellow_traveller.usecase.home

import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.domain.car.Car

class GetUserCarsLocalUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(): MutableList<Car> =
        dataSource.getAllCarsLocal()
}