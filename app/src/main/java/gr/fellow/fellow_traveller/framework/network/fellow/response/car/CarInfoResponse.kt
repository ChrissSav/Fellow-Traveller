package gr.fellow.fellow_traveller.framework.network.fellow.response.car


data class CarInfoResponse(
    val id: String,
    val brand: String,
    val model: String,
    val plate: String,
    val color: String
)