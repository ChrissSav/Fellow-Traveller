package gr.fellow.fellow_traveller.ui.home.chat.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Message(
    var id: String,
    var text: String,
    var groupId: String,
    var timestamp: Long,
    var senderName: String,
    var senderImage: String
) : Parcelable



