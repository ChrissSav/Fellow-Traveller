package gr.fellow.fellow_traveller.framework

import gr.fellow.fellow_traveller.ConnectivityHelper
import gr.fellow.fellow_traveller.data.BaseApiException
import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.getModelFromResponseErrorBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException


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
        if (isInternetAvailable())
            performCall {
                function.invoke()
            }
        else
            throw BaseApiException(msg = "Δεν υπάρχει σύνδεση στο ίντερνετ")
    }
}


suspend fun <T : Any> performCall(
    connectivityHelper: ConnectivityHelper,
    call: suspend () -> ResultWrapper<T>
): ResultWrapper<T> =
    if (!connectivityHelper.checkInternetConnection()) {
        throw BaseApiException(msg = "Δεν υπάρχει σύνδεση στο ίντερνετ")
    } else {
        try {
            call.invoke()
        } catch (exception: Exception) {
            print(exception.message)
            ResultWrapper.Error(getModelFromResponseErrorBody())
        }
    }


fun isInternetAvailable(): Boolean {
    val command = "ping -c 1 google.com"
    return try {
        Runtime.getRuntime().exec(command).waitFor() == 0
    } catch (e: InterruptedException) {
        e.printStackTrace()
        false
    } catch (e: IOException) {
        e.printStackTrace()
        false
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


suspend fun <T : Any> roomCall(
    function: suspend () -> T
): T =
    withContext(Dispatchers.IO) {
        function.invoke()
    }

