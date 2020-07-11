package com.example.fellowtravellerbeta.data.network.response

import com.google.gson.annotations.SerializedName

data class StatusHandleResponse(

    val status:String,
    @SerializedName("msg")
    val message : String

)