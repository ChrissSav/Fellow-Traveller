package gr.fellow.fellow_traveller.utils

import gr.fellow.fellow_traveller.data.BaseApiException
import gr.fellow.fellow_traveller.data.BaseResponse
import gr.fellow.fellow_traveller.data.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response


fun <T : Any> Response<BaseResponse<T>>.handleApiFormat(): ResultWrapper<T> {
    val body = body()
    return if (isSuccessful && body != null) {
        if (body.status == "success")
            ResultWrapper.Success(body.data)
        else
            ResultWrapper.Error(body.error)
    } else {
        if (code() == 401)
            throw BaseApiException(ACCESS_DENIED)
        ResultWrapper.Error(getErrorResponseErrorBody(errorBody()))
    }
}


suspend fun <T : Any> networkCallWithOutWrap(
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


suspend fun <T : Any> networkCall(
    function: suspend () -> ResultWrapper<T>
): ResultWrapper<T> {
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






