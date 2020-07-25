package com.example.fellowtravellerbeta.data.network.google.response

import com.google.gson.annotations.SerializedName

data class PredictionResponse (
    @SerializedName("place_id")
    val placeId: String,
    var description: String
)