package gr.fellow.fellow_traveller.framework.network.fellow.response


data class AuthenticationResponse(
    val authenticationToken: String,
    val refreshToken: String,
    val accountInfo: UserAuthResponse

)

data class UserAuthResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val rate: Float,
    val reviews: Int,
    val picture: String?,
    val messengerLink: String?,
    val aboutMe: String?,
    val tripsOffers: Int,
    val tripsInvolved: Int
)