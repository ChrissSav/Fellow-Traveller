package gr.fellow.fellow_traveller.usecase.auth

import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.framework.network.fellow.request.UpdatePasswordRequest

class ChangePasswordUseCase(
    private val dataSource: FellowDataSource
) {
    suspend operator fun invoke(password: String) =
        dataSource.changePassword(UpdatePasswordRequest(password, password))


}