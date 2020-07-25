package gr.fellow.fellow_traveller.usecase

import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.framework.network.fellow.response.StatusHandleResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserInfoResponse

class RegisterUserUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        phone: String
    ): ResultWrapper<UserInfoResponse> {
        return dataSource.registerUser(firstName, lastName, email, password, phone)
    }

}