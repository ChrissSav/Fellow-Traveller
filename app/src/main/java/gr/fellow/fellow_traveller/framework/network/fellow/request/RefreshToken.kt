package gr.fellow.fellow_traveller.framework.network.fellow.request


data class RefreshTokenRequest(
    val refreshToken: String
)

data class AccessRefreshTokenResponse(
    val accessToken: String,
    val refreshToken: String
)