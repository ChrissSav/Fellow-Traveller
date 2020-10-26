package gr.fellow.fellow_traveller.domain

enum class TripType {
    Offer, TakesPart
}


enum class PetAnswerType {
    Yes, No
}


enum class AnswerType {
    Yes, No
}


enum class BagsStatusType(val value: String) {
    NONE("Καθόλου"),
    LIMITED("Περιορισμένη"),
    LARGE("Μεγάλη")
}
