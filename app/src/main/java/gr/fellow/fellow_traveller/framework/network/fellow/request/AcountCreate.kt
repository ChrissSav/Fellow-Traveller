package gr.fellow.fellow_traveller.framework.network.fellow.request


data class CheckEmailRequest(
    val email: String
)

data class AccountCreateRequest(
    val firstName: String,
    val lastName: String,
    val email:String,
    val password: String
)