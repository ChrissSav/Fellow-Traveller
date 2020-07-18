package com.example.fellowtravellerbeta.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}
val Result<*>.succeeded
    get() = this is Result.Success && data != null


suspend fun <T : Any> performCall(
    function: suspend () -> T
): T {
    return withContext(Dispatchers.IO) {
        function.invoke()
    }
}