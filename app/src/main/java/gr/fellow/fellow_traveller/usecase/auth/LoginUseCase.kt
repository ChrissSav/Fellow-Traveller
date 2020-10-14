package gr.fellow.fellow_traveller.usecase.auth

import gr.fellow.fellow_traveller.data.ResultWrapperSecond
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserAuthResponse

class LoginUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(username: String, password: String): ResultWrapperSecond<UserAuthResponse> {
        return dataSource.loginUser(username, password)
    }

}