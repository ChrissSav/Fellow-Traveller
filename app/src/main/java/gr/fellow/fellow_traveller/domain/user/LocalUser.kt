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
    val messengerLink: String?,
    val tripsOffers: Int,
    val tripsInvolved: Int
) {
    val fullName get() = "$firstName $lastName"
}