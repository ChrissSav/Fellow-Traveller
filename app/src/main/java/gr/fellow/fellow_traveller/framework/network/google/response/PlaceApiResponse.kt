package gr.fellow.fellow_traveller.framework.network.google.response

import com.example.fellowtravellerbeta.data.network.google.response.PredictionResponse

data  class PlaceApiResponse (
    val predictions: MutableList<PredictionResponse>
)