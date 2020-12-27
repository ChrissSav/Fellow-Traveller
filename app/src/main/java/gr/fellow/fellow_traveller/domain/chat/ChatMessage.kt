package gr.fellow.fellow_traveller.domain.chat

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@Parcelize
@IgnoreExtraProperties
data class ChatMessage(

    val senderId: String = "",
    val text: String = "",
    val tripId: String = "",
    val timestamp: Long = 0,
    val senderName: String = "",
    var senderImage: String = ""
) : Parcelable


