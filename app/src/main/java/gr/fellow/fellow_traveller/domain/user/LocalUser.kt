package gr.fellow.fellow_traveller.domain.user


data class LocalUser(
    val id: String,
    val firstName: String,
    val lastName: String,
    val rate: Float,
    val reviews: Int,
    val picture: String?,
    val aboutMe: String?,
    val email: String,
    val messengerLink: String?
) {
    val fullName get() = "$firstName $lastName"
}