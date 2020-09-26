package gr.fellow.fellow_traveller.domain


data class LocalUser(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val rate: Float,
    val reviews: Int,
    val picture: String?,
    val aboutMe: String?,
    val phone: String,
    val email: String
) {
    val fullName get() = "$firstName $lastName"
}