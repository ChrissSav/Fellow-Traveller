package gr.fellow.fellow_traveller.framework.network.fellow.response

import gr.fellow.fellow_traveller.framework.network.fellow.response.DetailResponse

data class ErrorResponseModel (
    val status: String,
    val detail : DetailResponse
)


data class ErrorResponse (
    var code: Int,
    var msg : String?
)

