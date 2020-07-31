package gr.fellow.fellow_traveller.usecase.home

import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.framework.network.fellow.response.CarResponse
import gr.fellow.fellow_traveller.room.entites.CarEntity

class RegisterCarLocalUseCase (
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(carEntity: CarEntity) {
        return dataSource.insertCar(carEntity)
    }

}