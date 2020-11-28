package gr.fellow.fellow_traveller.framework.network.fellow.user

data class UserCreatorResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
    val picture: String?,
    val messengerLink: String,
    val rate: Float,
    val reviews: Int
) {
    val fullName get() = "$firstName $lastName"
}