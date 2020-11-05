package gr.fellow.fellow_traveller.usecase.user

import gr.fellow.fellow_traveller.domain.FellowDataSource

class GetUserInfoByIdUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(userId: String) =
        dataSource.getUserInfoById(userId)


}