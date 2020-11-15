package gr.fellow.fellow_traveller.usecase.home

import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.framework.network.fellow.car.CarRequest

class AddCarToRemoteUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(brand: String, model: String, plate: String, color: String) =
        dataSource.addCarRemote(CarRequest(brand, model, plate, color))


}