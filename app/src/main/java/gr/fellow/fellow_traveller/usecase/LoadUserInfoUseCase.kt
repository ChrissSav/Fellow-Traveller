package gr.fellow.fellow_traveller.usecase

import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.domain.LocalUser

class LoadUserInfoUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(): LocalUser {
        return dataSource.loadUsersInfoLocal()
    }

}