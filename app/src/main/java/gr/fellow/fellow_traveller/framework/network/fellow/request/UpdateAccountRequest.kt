package gr.fellow.fellow_traveller.framework.network.fellow.request

data class UpdateAccountRequest(
    val firstName: String,
    val lastName: String,
    val picture: String?,
    val messengerLink: String?,
    val aboutMe: String?
)