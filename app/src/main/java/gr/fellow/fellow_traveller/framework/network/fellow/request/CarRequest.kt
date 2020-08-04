package gr.fellow.fellow_traveller.framework.network.fellow.request

data class CarRequest(
    val brand : String,
    val model : String,
    val plate : String,
    val color : String
)