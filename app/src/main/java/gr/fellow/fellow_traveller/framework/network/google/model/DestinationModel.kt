package gr.fellow.fellow_traveller.framework.network.google.model


data class DestinationModel(
    val placeId: String,
    val title: String
)

data class PlaceModel(
    val placeId: String,
    val title: String,
    val latitude: Float,
    val longitude: Float
)
