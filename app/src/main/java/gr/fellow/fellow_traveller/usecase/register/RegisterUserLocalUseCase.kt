package gr.fellow.fellow_traveller.usecase.register

import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.domain.user.LocalUser
import gr.fellow.fellow_traveller.framework.network.fellow.user.UserAuthResponse


class RegisterUserLocalUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(userAuthResponse: UserAuthResponse) {
        dataSource.registerUserAuth(userAuthResponse)
    }

    suspend operator fun invoke(userLocal: LocalUser) {
        dataSource.registerUserAuth(userLocal)
    }

}