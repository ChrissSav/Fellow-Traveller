package com.example.fellowtravellerbeta.data.network.request

import com.google.gson.annotations.SerializedName

data class AccountCheckRequest(
    val item: String,
    val value: String
)

data class AccountCreateRequest(
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val email:String,
    val password: String,
    val phone : String
)