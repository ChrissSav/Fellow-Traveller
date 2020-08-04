package gr.fellow.fellow_traveller.usecase.home

import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.framework.network.fellow.response.CarResponse

class GetUserCarsRemoteUseCase (
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(): ResultWrapper<ArrayList<CarResponse>> {
        return dataSource.getCarsRemote()
    }

}