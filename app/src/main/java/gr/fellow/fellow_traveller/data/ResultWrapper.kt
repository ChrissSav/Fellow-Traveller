package gr.fellow.fellow_traveller.data

import gr.fellow.fellow_traveller.framework.network.fellow.response.ErrorResponse

sealed class ResultWrapper<out T : Any> {

    data class Success<out T : Any>(val data: T) : ResultWrapper<T>()

    data class Error(var error: ErrorResponse) : ResultWrapper<Nothing>()
}


