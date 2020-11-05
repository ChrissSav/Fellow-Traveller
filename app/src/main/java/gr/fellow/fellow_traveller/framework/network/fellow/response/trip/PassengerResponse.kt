package gr.fellow.fellow_traveller.framework.network.fellow.response.trip

import gr.fellow.fellow_traveller.framework.network.fellow.response.user.UserBaseResponse

data class PassengerResponse(
    val user: UserBaseResponse,
    val seats: Int,
    val pet: Boolean
)