package gr.fellow.fellow_traveller.ui.home.chat.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Conversation(
    val tripId: String = "",
    val tripName: String = "",
    val description: String = "",
    val timestamp: Long = 0,
    val image: String = "",
    val seen: Boolean = false
) : Parcelable