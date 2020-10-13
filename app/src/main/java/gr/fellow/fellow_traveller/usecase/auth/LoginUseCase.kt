package gr.fellow.fellow_traveller.usecase.auth

import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserLoginResponse

class LoginUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(username: String, password: String): ResultWrapper<UserLoginResponse> {
        return dataSource.loginUser(username, password)
    }

}