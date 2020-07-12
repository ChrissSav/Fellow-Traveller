package com.example.fellowtravellerbeta.data.network.response

import com.google.gson.annotations.SerializedName

data class DetailResponse (
    @SerializedName("status_code")
    val statusCode : Int,
    @SerializedName("msg")
    val message : String
)