package gr.fellow.fellow_traveller.usecase.notification

import gr.fellow.fellow_traveller.domain.FellowDataSource

class GetNotificationsSocketUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke() =
        dataSource.getNotificationSocket()

}
