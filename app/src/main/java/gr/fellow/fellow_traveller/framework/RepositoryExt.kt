package gr.fellow.fellow_traveller.framework

import gr.fellow.fellow_traveller.ConnectivityHelper
import gr.fellow.fellow_traveller.data.BaseApiException
import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.getModelFromResponseErrorBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response


fun <T : Any> Response<T>.handleToCorrectFormat(): ResultWrapper<T> {
    val body = body()
    return if (isSuccessful && body != null) {
        ResultWrapper.Success(body)
    } else {
        ResultWrapper.Error(getModelFromResponseErrorBody(errorBody()))
    }
}



suspend fun <T : Any> networkCall(
    connectivityHelper: ConnectivityHelper,
    function: suspend () -> ResultWrapper<T>
): ResultWrapper<T> {
    return withContext(Dispatchers.IO) {
        performCall(connectivityHelper) {
            function.invoke()
        }
    }
}

suspend fun <T : Any> networkCall(
    function: suspend () -> ResultWrapper<T>
): ResultWrapper<T> {
    return withContext(Dispatchers.IO) {
        performCall {
            function.invoke()
        }
    }
}


suspend fun <T : Any> performCall(
    connectivityHelper: ConnectivityHelper,
    call: suspend () -> ResultWrapper<T>
): ResultWrapper<T> =
    if (!connectivityHelper.checkInternetConnection()) {
        throw BaseApiException(500)
    } else {
        try {
            call.invoke()
        } catch (exception: Exception) {
            ResultWrapper.Error(getModelFromResponseErrorBody())
        }
    }


suspend fun <T : Any> performCallWithOut(
    function: suspend () -> T
): T {
    return withContext(Dispatchers.IO) {
        function.invoke()
    }
}


suspend fun <T : Any> performCall(
    call: suspend () -> ResultWrapper<T>
): ResultWrapper<T> =
    try {
        call.invoke()
    } catch (exception: Exception) {
        ResultWrapper.Error(getModelFromResponseErrorBody())
    }



