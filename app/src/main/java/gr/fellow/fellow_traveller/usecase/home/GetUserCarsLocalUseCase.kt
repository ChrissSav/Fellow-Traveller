package gr.fellow.fellow_traveller.usecase.home

import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.room.entites.CarEntity

class GetUserCarsLocalUseCase  (
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(): MutableList<CarEntity> =
         dataSource.getAllCars()


}