package gr.fellow.fellow_traveller.framework.network.fellow.auth

data class LogoutRequest(
    val refreshToken: String
)