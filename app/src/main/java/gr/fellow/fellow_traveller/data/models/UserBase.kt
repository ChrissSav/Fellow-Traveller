package gr.fellow.fellow_traveller.data.models


data class UserBase(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val picture: String?,
    val rate: Float,
    val reviews: Int
) {
    val fullName get() = "$firstName $lastName"
}