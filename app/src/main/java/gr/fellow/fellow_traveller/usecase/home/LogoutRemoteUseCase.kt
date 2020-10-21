package gr.fellow.fellow_traveller.usecase.home

import gr.fellow.fellow_traveller.domain.FellowDataSource

class LogoutRemoteUseCase(
    private val dataSource: FellowDataSource
) {
    suspend operator fun invoke() =
        dataSource.logoutRemote()
}