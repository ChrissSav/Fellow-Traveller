package gr.fellow.fellow_traveller.framework.network.fellow.response

data class PassengerResponse(
    val user: UserBaseResponse,
    val bags: Int,
    val pet: Boolean
)