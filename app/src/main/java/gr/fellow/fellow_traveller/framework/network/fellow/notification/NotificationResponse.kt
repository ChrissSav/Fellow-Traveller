package gr.fellow.fellow_traveller.framework.network.fellow.notification

import gr.fellow.fellow_traveller.framework.network.fellow.user.UserBaseResponse

data class NotificationResponse(
    val id: Long,
    val user: UserBaseResponse,
    val type: Int,
    val isRead: Boolean,
    val trip: NotificationTripResponse,
    val timestamp: Long
)

data class NotificationTripResponse(
    val id: String,
    val destinationFrom: String,
    val destinationTo: String
)

data class UpdateNotification(
    val id: Long
)