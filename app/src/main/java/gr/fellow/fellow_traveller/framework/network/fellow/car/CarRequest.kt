package gr.fellow.fellow_traveller.framework.network.fellow.car

data class CarRequest(
    val brand : String,
    val model : String,
    val plate : String,
    val color : String
)