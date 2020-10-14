package gr.fellow.fellow_traveller.usecase.register

import gr.fellow.fellow_traveller.data.ResultWrapperSecond
import gr.fellow.fellow_traveller.domain.FellowDataSource

class RegisterUserUseCase(
    private val dataSource: FellowDataSource
) {
    suspend operator fun invoke(firstName: String, lastName: String, email: String, password: String): ResultWrapperSecond<String> =
        dataSource.registerUser(firstName, lastName, email, password)


}