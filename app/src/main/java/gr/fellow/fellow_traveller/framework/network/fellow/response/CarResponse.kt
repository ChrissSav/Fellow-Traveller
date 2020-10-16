package gr.fellow.fellow_traveller.framework.network.fellow.response


data class CarResponse(
    val id: String,
    val brand: String,
    val model: String,
    val plate: String,
    val color: String
)