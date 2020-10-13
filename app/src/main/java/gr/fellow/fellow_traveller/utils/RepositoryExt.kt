package gr.fellow.fellow_traveller.utils

import gr.fellow.fellow_traveller.data.BaseApiException
import gr.fellow.fellow_traveller.data.BaseResponse
import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.data.ResultWrapperSecond
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response


fun <T : Any> Response<T>.handleToCorrectFormat(): ResultWrapper<T> {
    val body = body()
    return if (isSuccessful && body != null) {
        ResultWrapper.Success(body)
    } else {
        if (code() == 401)
            throw BaseApiException(ACCESS_DENIED)
        ResultWrapper.Error(getModelFromResponseErrorBody(errorBody()))
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


/*** From the new api format */


suspend fun <T : Any> networkCallSecond(
    function: suspend () -> ResultWrapperSecond<T>
): ResultWrapperSecond<T> {
    return withContext(Dispatchers.IO) {
        try {
            function.invoke()
        } catch (exception: BaseApiException) {
            throw exception
        }
    }
}


fun <T : Any> Response<BaseResponse<T>>.handleApiFormat(): ResultWrapperSecond<T> {
    val body = body()
    return if (isSuccessful && body != null) {
        if (body.status == "success")
            ResultWrapperSecond.Success(body.data)
        else
            ResultWrapperSecond.Error(body.error)
    } else {
        if (code() == 401)
            throw BaseApiException(ACCESS_DENIED)
        ResultWrapperSecond.Error(getErrorResponseErrorBody(errorBody()))
    }
}

