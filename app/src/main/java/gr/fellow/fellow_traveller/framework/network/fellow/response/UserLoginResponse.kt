package gr.fellow.fellow_traveller.framework.network.fellow.response

import com.google.gson.annotations.SerializedName

data class UserLoginResponse(

    val id: Int,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val reviews: Int,
    val rate: Float,
    val picture: String,
    @SerializedName("about_me")
    val aboutMe: String,
    val phone: String,
    @SerializedName("email")
    val emailAddress: String

)

