package gr.fellow.fellow_traveller.domain

import gr.fellow.fellow_traveller.R


enum class PetAnswerType {
    Yes, No, Anything
}


enum class SortAnswerType {
    Price, Relevant, Rate
}

enum class AnswerType {
    Yes, No
}


enum class BagsStatusType(val textInt: Int, val code: Int) {
    NONE(R.string.luggage_availability_none, 0),
    LIMITED(R.string.luggage_availability_limited, 1),
    LARGE(R.string.luggage_availability_big, 2)
}
