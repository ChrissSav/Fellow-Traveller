package gr.fellow.fellow_traveller.usecase.trips

import gr.fellow.fellow_traveller.domain.FellowDataSource

class GetTripsAsCreatorRemoteUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(status: String, page: Int) =
        dataSource.getTipsAsCreator(status, page)

}