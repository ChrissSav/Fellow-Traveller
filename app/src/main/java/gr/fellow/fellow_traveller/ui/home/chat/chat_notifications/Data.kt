package gr.fellow.fellow_traveller.ui.home.chat.chat_notifications

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@Parcelize
@IgnoreExtraProperties
data class Data(
    private var user: String? = "",
    private var icon: Int = 0,
    private var body: String? = "",
    private var title: String? = "",
    private var sented: String? = "",
) : Parcelable





