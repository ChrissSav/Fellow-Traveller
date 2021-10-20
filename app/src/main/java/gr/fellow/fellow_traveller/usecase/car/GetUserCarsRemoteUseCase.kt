package gr.fellow.fellow_traveller.usecase.car

import gr.fellow.fellow_traveller.domain.FellowDataSource

class GetUserCarsRemoteUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke() =
        dataSource.getCarsRemote()

}