package com.example.fellowtravellerbeta.data.network.response

import com.google.gson.annotations.SerializedName

data class UserInfoResponse(
    val id: Int,
    @SerializedName("first_name")
    val FirstName: String,
    @SerializedName("last_name")
    val LastName: String,
    val email: String,
    val phone: String,
    val picture: String,
    @SerializedName("about_me")
    val aboutMe: String,
    val rate: Int,
    val reviews: Float
)
