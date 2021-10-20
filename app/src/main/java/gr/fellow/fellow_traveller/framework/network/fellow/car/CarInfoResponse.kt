package gr.fellow.fellow_traveller.framework.network.fellow.car


data class CarInfoResponse(
    val id: String,
    val brand: String,
    val model: String,
    val plate: String,
    val color: CarColorResponse,
)


data class CarColorResponse(
    val title: String,
    val hex: String,
)
