package gr.fellow.fellow_traveller.usecase.register

import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.domain.mappers.mapToRegisteredUserEntity
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserAuthResponse


class RegisterUserLocalUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(userAuthResponse: UserAuthResponse) {
        val user = userAuthResponse.mapToRegisteredUserEntity()
        dataSource.registerUserAuth(user)
    }

}