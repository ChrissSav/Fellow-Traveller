package gr.fellow.fellow_traveller.domain.notification

import gr.fellow.fellow_traveller.domain.user.UserBase


data class Notification(
    val id: Long,
    val user: UserBase,
    val type: Int,
    val isRead: Boolean,
    val trip: NotificationTrip,
    val timestamp: Long
)

data class NotificationTrip(
    val id: String,
    val destinationFrom: String,
    val destinationTo: String
)