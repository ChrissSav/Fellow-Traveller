package gr.fellow.fellow_traveller.framework.network.fellow.response


data class ErrorResponseModel(
    val status: String,
    val detail: DetailResponse
)


data class ErrorResponse(
    var code: Int = 10000,
    var msg: String = "Αδυναμία επικοινωνίας με το API."
)





