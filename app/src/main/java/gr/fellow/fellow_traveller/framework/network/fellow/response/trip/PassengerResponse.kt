package gr.fellow.fellow_traveller.framework.network.fellow.response.trip

import gr.fellow.fellow_traveller.framework.network.fellow.response.UserBaseResponse

data class PassengerResponse(
    val user: UserBaseResponse,
    val seats: Int,
    val pet: Boolean
)