package gr.fellow.fellow_traveller.usecase

import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.room.entites.RegisteredUserEntity

class LoadUserInfoUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(): RegisteredUserEntity {
        return dataSource.loadUsersInfoLocal()
    }

}