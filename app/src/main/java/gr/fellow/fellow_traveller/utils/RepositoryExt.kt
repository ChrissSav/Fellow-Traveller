package gr.fellow.fellow_traveller.utils

import gr.fellow.fellow_traveller.data.BaseApiException
import gr.fellow.fellow_traveller.data.BaseResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response


fun <T : Any> Response<BaseResponse<T>>.handleApiFormat(): T {
    val body = body()
    return if (isSuccessful && body != null) {
        if (body.status == "success")
            body.data
        else
            throw BaseApiException(text = body.error)
    } else {
        throw BaseApiException(text = getErrorResponseErrorBody(errorBody()))
    }
}


suspend fun <T : Any> networkCall(
    function: suspend () -> T
): T {
    return withContext(Dispatchers.IO) {
        try {
            function.invoke()
        } catch (exception: BaseApiException) {
            throw exception
        }
    }
}


suspend fun <T : Any> roomCall(
    function: suspend () -> T
): T =
    withContext(Dispatchers.IO) {
        function.invoke()
    }






