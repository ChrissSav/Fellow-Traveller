package gr.fellow.fellow_traveller.usecase

import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.domain.user.LocalUser

class LoadUserLocalInfoUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(): LocalUser {
        return dataSource.loadUsersInfoLocal()
    }

}