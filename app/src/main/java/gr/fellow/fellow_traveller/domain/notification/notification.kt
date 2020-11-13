package gr.fellow.fellow_traveller.domain.notification

import android.os.Parcelable
import gr.fellow.fellow_traveller.domain.user.UserBase
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Notification(
    val id: Long,
    val user: UserBase,
    val type: Int,
    var isRead: Boolean,
    val trip: NotificationTrip,
    val timestamp: Long
) : Parcelable

@Parcelize
data class NotificationTrip(
    val id: String,
    val destinationFrom: String,
    val destinationTo: String
) : Parcelable