package gr.fellow.fellow_traveller.usecase.notification

import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.framework.network.fellow.notification.UpdateNotification

class UpdateNotificationsUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(id: Long) =
        dataSource.setNotificationRead(UpdateNotification(id))

}
