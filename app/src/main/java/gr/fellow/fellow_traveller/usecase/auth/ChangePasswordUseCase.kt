package gr.fellow.fellow_traveller.usecase.auth

import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.framework.network.fellow.user.UpdatePasswordRequest

class ChangePasswordUseCase(
    private val dataSource: FellowDataSource
) {
    suspend operator fun invoke(prevPassword: String, newPassword: String) =
        dataSource.changePassword(UpdatePasswordRequest(prevPassword, newPassword, newPassword))


}