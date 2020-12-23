package gr.fellow.fellow_traveller.data

import java.io.IOException


open class BaseApiException(open val code: Int? = null, open val text: String? = null) : Exception()

open class BaseFirebaseException() : Exception()

open class NoInternetException : IOException()

open class UnauthorizedException : IOException()