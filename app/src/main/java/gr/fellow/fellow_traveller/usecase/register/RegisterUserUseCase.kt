package gr.fellow.fellow_traveller.usecase.register

import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserLoginResponse

class RegisterUserUseCase(
    private val dataSource: FellowDataSource
) {
    suspend operator fun invoke(
        firstName: String, lastName: String, email: String, password: String, phone: String
    ): ResultWrapper<UserLoginResponse> {

        return dataSource.registerUser(firstName, lastName, email, password, phone)
    }

}