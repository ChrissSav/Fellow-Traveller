package gr.fellow.fellow_traveller.usecase.auth

import gr.fellow.fellow_traveller.domain.FellowDataSource

class VerifyAccountUseCase(
    private val dataSource: FellowDataSource
) {
    suspend operator fun invoke(token: String) =
        dataSource.verifyAccount(token)
}