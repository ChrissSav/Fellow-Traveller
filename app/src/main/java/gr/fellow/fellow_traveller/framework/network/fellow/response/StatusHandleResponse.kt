package gr.fellow.fellow_traveller.framework.network.fellow.response

import com.google.gson.annotations.SerializedName

data class StatusHandleResponse(

    val status:String,
    @SerializedName("msg")
    val message : String

)