package com.example.fellowtravellerbeta.data.network.fellow.request

import com.google.gson.annotations.SerializedName

data class TripCreateRequest(
    @SerializedName("destFrom")
    val destFrom: String,

    @SerializedName("destTo")
    val destTo: String,

    @SerializedName("pickup_point")
    val PickUP: String,

    @SerializedName("timestamp")
    val timestamp: Long,

    @SerializedName("pet")
    val hasPet: Boolean,

    @SerializedName("max_seats")
    val maxSeats: Int,

    @SerializedName("max_bags")
    val maxBags: Int,

    @SerializedName("message")
    val msg: String,

    @SerializedName("price")
    val price: Float,

    @SerializedName("car_id")
    val carId: Int
)