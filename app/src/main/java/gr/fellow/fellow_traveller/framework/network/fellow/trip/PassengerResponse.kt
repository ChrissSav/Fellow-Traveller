package gr.fellow.fellow_traveller.framework.network.fellow.trip

import gr.fellow.fellow_traveller.framework.network.fellow.user.UserBaseResponse

data class PassengerResponse(
    val user: UserBaseResponse,
    val seats: Int,
    val pet: Boolean
)