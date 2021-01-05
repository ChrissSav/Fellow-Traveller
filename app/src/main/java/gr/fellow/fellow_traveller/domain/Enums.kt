package gr.fellow.fellow_traveller.domain

import gr.fellow.fellow_traveller.R


enum class PetAnswerType {
    Yes, No, Anything
}

enum class TripStatus(val textInt: Int) {
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
    Price(R.string.price_title),
    Relevant(R.string.most_relevant),
    Rate(R.string.rating)
}

enum class AnswerType {
    Yes, No
}


enum class BagsStatusType(val textInt: Int, val code: Int) {
    NONE(R.string.availability_none, 0),
    LIMITED(R.string.availability_limited, 1),
    LARGE(R.string.availability_great, 2)
}
