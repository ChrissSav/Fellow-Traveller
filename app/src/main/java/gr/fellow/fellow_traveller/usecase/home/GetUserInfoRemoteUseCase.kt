package gr.fellow.fellow_traveller.usecase.home

import gr.fellow.fellow_traveller.domain.FellowDataSource

class GetUserInfoRemoteUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke() =
        dataSource.getUserInfoRemote()

}