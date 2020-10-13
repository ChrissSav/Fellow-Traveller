package gr.fellow.fellow_traveller.data

/*** From the new api format */


sealed class ResultWrapperSecond<out T : Any> {

    data class Success<out T : Any>(val data: T) : ResultWrapperSecond<T>()

    data class Error(var error: String) : ResultWrapperSecond<Nothing>()
}


data class BaseResponse<T>(
    val status: String,
    val data: T,
    val error: String
)