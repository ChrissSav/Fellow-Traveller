package gr.fellow.fellow_traveller.ui.home.chat.chat_notifications

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Sender(
    var data: Data,
    var to: String,
) : Parcelable
