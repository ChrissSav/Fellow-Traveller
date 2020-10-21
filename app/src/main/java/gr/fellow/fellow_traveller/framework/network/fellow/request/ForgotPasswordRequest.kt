package gr.fellow.fellow_traveller.framework.network.fellow.request

data class ForgotPasswordRequest(
    val email: String
)

data class ResetPasswordRequest(
    val token: String,
    val password: String,
    val repeatPassword: String
)