package gr.fellow.fellow_traveller.usecase.auth

import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.domain.FellowDataSource

class VerifyAccountUseCase(
    private val dataSource: FellowDataSource
) {
    suspend operator fun invoke(token: String): ResultWrapper<String> =
        dataSource.verifyAccount(token)


}