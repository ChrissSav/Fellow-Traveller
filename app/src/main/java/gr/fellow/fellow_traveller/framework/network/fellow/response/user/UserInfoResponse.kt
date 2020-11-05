package gr.fellow.fellow_traveller.framework.network.fellow.response.user


data class UserInfoResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
    val rate: Float,
    val reviews: Int,
    val picture: String?,
    val messengerLink: String?,
    val aboutMe: String?,
    val tripsOffers: Int,
    val tripsInvolved: Int
)