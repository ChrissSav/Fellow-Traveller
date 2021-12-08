package gr.fellow.fellow_traveller.domain.notification

import android.os.Parcelable
import gr.fellow.fellow_traveller.domain.NotificationStatus
import gr.fellow.fellow_traveller.domain.TripStatus
import gr.fellow.fellow_traveller.domain.user.UserBase
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Notification(
    val id: String,
    val user: UserBase,
    val type: NotificationStatus,
    var isRead: Boolean,
    val trip: NotificationTrip,
    val timestamp: Long,
) : Parcelable

@Parcelize
data class NotificationTrip(
    val id: String,
    val destinationFrom: String,
    val destinationTo: String,
    val status: TripStatus,
) : Parcelable