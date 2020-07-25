package gr.fellow.fellow_traveller.framework.network.fellow.response

import com.google.gson.annotations.SerializedName

data class DetailResponse (
    @SerializedName("status_code")
    val statusCode : Int,
    @SerializedName("msg")
    val message : String
)