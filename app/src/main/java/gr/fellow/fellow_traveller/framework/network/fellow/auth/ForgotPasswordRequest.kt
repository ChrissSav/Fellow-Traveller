package gr.fellow.fellow_traveller.framework.network.fellow.auth

data class ForgotPasswordRequest(
    val email: String
)

data class ResetPasswordRequest(
    val email: String,
    val token: String,
    val password: String,
    val repeatPassword: String
)