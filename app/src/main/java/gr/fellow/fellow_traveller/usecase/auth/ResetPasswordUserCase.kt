package gr.fellow.fellow_traveller.usecase.auth

import gr.fellow.fellow_traveller.domain.FellowDataSource

class ResetPasswordUserCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(email: String, code: String, password: String) =
        dataSource.resetPassword(email, code, password)


}