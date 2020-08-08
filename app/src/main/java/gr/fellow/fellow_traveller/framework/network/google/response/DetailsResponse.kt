package gr.fellow.fellow_traveller.framework.network.google.response


data class DetailsResponse(
    val result: ResultResponse
)

data class ResultResponse(
    val geometry: GeometryResponse
)

data class GeometryResponse(
    val location: LocationResponse
)

data class LocationResponse(
    val lat: Float,
    val lng: Float
)