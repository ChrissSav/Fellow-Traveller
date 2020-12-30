package gr.fellow.fellow_traveller.ui.home.chat.chat_notifications

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Token(
    val token: String = "",
) : Parcelable
