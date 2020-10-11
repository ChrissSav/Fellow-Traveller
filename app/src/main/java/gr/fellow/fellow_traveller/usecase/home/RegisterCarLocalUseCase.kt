package gr.fellow.fellow_traveller.usecase.home

import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.domain.car.Car

class RegisterCarLocalUseCase (
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(carEntity: Car) {
        return dataSource.insertCarLocal(carEntity)
    }

}