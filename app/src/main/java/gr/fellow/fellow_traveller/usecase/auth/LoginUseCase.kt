package gr.fellow.fellow_traveller.usecase.auth

import gr.fellow.fellow_traveller.domain.FellowDataSource

class LoginUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(username: String, password: String) =
        dataSource.loginUser(username, password)


}