package gr.fellow.fellow_traveller.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


enum class PetAnswerType {
    Yes, No, Anything
}


enum class SortAnswerType {
    Price, Relevant, Rate
}

enum class AnswerType {
    Yes, No
}


@Parcelize
enum class TripType : Parcelable {
    CREATOR, PASSENGER
}


enum class BagsStatusType(val value: String) {
    NONE("Καθόλου"),
    LIMITED("Περιορισμένη"),
    LARGE("Μεγάλη")
}
