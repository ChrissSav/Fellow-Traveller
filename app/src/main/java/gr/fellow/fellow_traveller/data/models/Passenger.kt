package gr.fellow.fellow_traveller.data.models


data class Passenger(
    val user: UserBase,
    val bags: Int,
    val pet: Boolean
)