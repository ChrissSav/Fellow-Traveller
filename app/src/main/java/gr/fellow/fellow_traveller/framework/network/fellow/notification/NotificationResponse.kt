package gr.fellow.fellow_traveller.framework.network.fellow.notification

import gr.fellow.fellow_traveller.domain.NotificationStatus
import gr.fellow.fellow_traveller.domain.TripStatus
import gr.fellow.fellow_traveller.framework.network.fellow.user.UserBaseResponse

data class NotificationResponse(
    val id: String,
    val user: UserBaseResponse,
    val type: Int,
    val read: Boolean,
    val trip: NotificationTripResponse,
    val timestamp: Long,
) {

    fun getNotificationStatus(): NotificationStatus =
        when (type) {
            0 -> {
                NotificationStatus.RATE
            }
            1 -> {
                NotificationStatus.PASSENGER_EXIT
            }
            2 -> {
                NotificationStatus.PASSENGER_ENTER
            }
            else -> {
                NotificationStatus.TRIP_DELETED
            }
        }


}

data class NotificationTripResponse(
    val id: String,
    val destinationFrom: String,
    val destinationTo: String,
    val status: Int,
) {
    fun getTripStatus(): TripStatus =
        when (status) {
            0 -> {
                TripStatus.ACTIVE
            }
            1 -> {
                TripStatus.PENDING
            }
            2 -> {
                TripStatus.COMPLETED
            }
            else -> {
                TripStatus.DELETED
            }
        }
}

data class UpdateNotification(
    val id: String
)