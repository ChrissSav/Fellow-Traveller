package gr.fellow.fellow_traveller.framework.network.fellow.response

import gr.fellow.fellow_traveller.R


data class ErrorResponseModel(
    val status: String,
    val detail: DetailResponse
)


data class ErrorResponse(
    var code: Int = 10000,
    var msg: Int = R.string.ERROR_SOMETHING_WRONG
)





