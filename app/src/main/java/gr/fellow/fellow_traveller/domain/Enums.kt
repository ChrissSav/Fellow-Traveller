package gr.fellow.fellow_traveller.domain

import android.os.Parcelable
import gr.fellow.fellow_traveller.R
import kotlinx.android.parcel.Parcelize


enum class PetAnswerType {
    Yes, No, Anything
}

@Parcelize
enum class TripStatus(val textInt: Int) : Parcelable {
    ACTIVE(R.string.trip_status_active),
    PENDING(R.string.trip_status_in_progress),
    COMPLETED(R.string.trip_status_complete),
    DELETED(R.string.trip_status_deleted)
}

enum class NotificationStatus {
    RATE,
    PASSENGER_EXIT,
    PASSENGER_ENTER,
    TRIP_DELETED
}

enum class SortAnswerType(val textInt: Int) {
    PRICE(R.string.price_title),
    RELEVANT(R.string.most_relevant),
    RATE(R.string.rating)
}

enum class AnswerType {
    Yes, No
}


enum class BagsStatusType(val textInt: Int, val code: Int) {
    NONE(R.string.availability_none, 0),
    LIMITED(R.string.availability_limited, 1),
    LARGE(R.string.availability_great, 2)
}


enum class PasswordStrength(val textInt: Int, val colorInt: Int) {
    WEAK(R.string.weak, R.color.red_color),
    MEDIUM(R.string.medium, R.color.orange_new),
    STRONG(R.string.strong, R.color.yellow),
    VERY_STRONG(R.string.very_strong, R.color.green)
}