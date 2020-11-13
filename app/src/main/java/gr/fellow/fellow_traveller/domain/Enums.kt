package gr.fellow.fellow_traveller.domain




enum class PetAnswerType {
    Yes, No, Anything
}


enum class SortAnswerType {
    Price, Relevant, Rate
}

enum class AnswerType {
    Yes, No
}


enum class BagsStatusType(val value: String) {
    NONE("Καθόλου"),
    LIMITED("Περιορισμένη"),
    LARGE("Μεγάλη")
}
