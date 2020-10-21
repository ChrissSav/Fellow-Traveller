package gr.fellow.fellow_traveller.usecase.auth

import gr.fellow.fellow_traveller.domain.FellowDataSource

class ForgotPasswordUserCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(email: String) =
        dataSource.forgotPassword(email)


}