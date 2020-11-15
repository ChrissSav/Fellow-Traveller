package gr.fellow.fellow_traveller.data


data class BaseResponse<T>(
    val status: String,
    val data: T,
    val error: String
)