package gr.fellow.fellow_traveller.framework.network.fellow.request

data class UpdateAccountRequest(
    val firstName: String,
    val lastName: String,
    val messengerLink: String?,
    val aboutMe: String?
)

data class UpdatePictureRequest(
    val picture: String?
)