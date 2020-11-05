package gr.fellow.fellow_traveller.data

/*** From the new api format */


sealed class ResultWrapper<out T : Any> {

    data class Success<out T : Any>(val data: T) : ResultWrapper<T>()

    data class Error(var error: String) : ResultWrapper<Nothing>()
}


data class BaseResponse<T>(
    val status: String,
    val data: T,
    val error: String
)