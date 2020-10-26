package gr.fellow.fellow_traveller.framework.network.fellow.response

import com.google.gson.annotations.SerializedName

data class UserBaseResponse(
    val id: String,
    @SerializedName("first_name")
    val FirstName: String,
    @SerializedName("last_name")
    val LastName: String,
    val picture: String?,
    val rate: Float,
    val reviews: Int
) {
    val fullName get() = "$FirstName $LastName"
}