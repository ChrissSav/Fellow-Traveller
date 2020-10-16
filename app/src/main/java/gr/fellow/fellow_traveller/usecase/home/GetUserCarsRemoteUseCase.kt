package gr.fellow.fellow_traveller.usecase.home

import gr.fellow.fellow_traveller.data.ResultWrapperSecond
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.domain.car.Car

class GetUserCarsRemoteUseCase (
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(): ResultWrapperSecond<MutableList<Car>> {
        return dataSource.getCarsRemote()
    }

}