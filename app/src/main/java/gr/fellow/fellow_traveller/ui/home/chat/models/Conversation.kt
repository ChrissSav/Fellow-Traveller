package gr.fellow.fellow_traveller.ui.home.chat.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Conversation(
    val tripId: String,
    val tripName: String,
    val description: String,
    val date: Long,
    val image: String,
    val isSeen: Boolean
) : Parcelable