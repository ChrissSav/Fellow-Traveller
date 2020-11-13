package gr.fellow.fellow_traveller.framework.network.fellow.auth

data class LoginRequest(
    val email : String,
    val password : String
)