package gr.fellow.fellow_traveller.usecase.home

import gr.fellow.fellow_traveller.domain.Car
import gr.fellow.fellow_traveller.domain.FellowDataSource

class RegisterCarLocalUseCase (
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(carEntity: Car) {
        return dataSource.insertCarLocal(carEntity)
    }

}