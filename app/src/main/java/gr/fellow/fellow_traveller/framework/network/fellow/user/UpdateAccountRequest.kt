package gr.fellow.fellow_traveller.framework.network.fellow.user

data class UpdateAccountRequest(
    val firstName: String,
    val lastName: String,
    val aboutMe: String?
)

data class UpdatePictureRequest(
    val picture: String?
)


data class UpdateMessengerRequest(
    val messenger: String
)


data class UpdatePasswordRequest(
    val previousPassword: String,
    val password: String,
    val repeatPassword: String
)