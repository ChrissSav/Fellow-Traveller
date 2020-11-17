package gr.fellow.fellow_traveller.usecase.home

import gr.fellow.fellow_traveller.domain.FellowDataSource

class UpdateUserMessengerUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(messenger: String) =
        dataSource.updateUserMessenger(messenger)


}