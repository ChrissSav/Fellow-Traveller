package gr.fellow.fellow_traveller.data

import java.io.IOException


open class BaseApiException(open var code: Int) : Exception()

open class NoInternetException : IOException()

open class UnauthorizedException : IOException()