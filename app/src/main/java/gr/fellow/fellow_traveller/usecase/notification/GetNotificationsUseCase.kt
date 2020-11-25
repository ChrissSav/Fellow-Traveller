package gr.fellow.fellow_traveller.usecase.notification

import gr.fellow.fellow_traveller.domain.FellowDataSource

class GetNotificationsUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke() =
        dataSource.getNotification()

}
