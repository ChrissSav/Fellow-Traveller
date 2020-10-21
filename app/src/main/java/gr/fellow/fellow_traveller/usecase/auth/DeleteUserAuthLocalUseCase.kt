package gr.fellow.fellow_traveller.usecase.auth

import gr.fellow.fellow_traveller.domain.FellowDataSource

class DeleteUserAuthLocalUseCase(
    private val dataSource: FellowDataSource
) {
    suspend operator fun invoke() =
        dataSource.logoutUserLocal()

}