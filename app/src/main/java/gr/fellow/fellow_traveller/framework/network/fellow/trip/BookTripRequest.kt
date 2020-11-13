package gr.fellow.fellow_traveller.framework.network.fellow.trip


data class BookTripRequest(
    val tripId: String,
    val seats: Int,
    val pet: Boolean
)