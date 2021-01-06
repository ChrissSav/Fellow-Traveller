package gr.fellow.fellow_traveller.framework.network.fellow.user


data class UserInfoResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
    val rate: Float,
    val reviews: Int,
    val picture: String?,
    val aboutMe: String?,
    val tripsOffers: Int,
    val tripsInvolved: Int
)