package gr.fellow.fellow_traveller.data

open class BaseApiException(
    open var code: Int = 10000,
    open val msg: String = "trtrtrt"
) : Exception()
