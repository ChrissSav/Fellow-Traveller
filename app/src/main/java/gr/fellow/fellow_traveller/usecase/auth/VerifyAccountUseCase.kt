package gr.fellow.fellow_traveller.usecase.auth

import gr.fellow.fellow_traveller.data.ResultWrapperSecond
import gr.fellow.fellow_traveller.domain.FellowDataSource

class VerifyAccountUseCase(
    private val dataSource: FellowDataSource
) {
    suspend operator fun invoke(token: String): ResultWrapperSecond<String> =
        dataSource.verifyAccount(token)


}