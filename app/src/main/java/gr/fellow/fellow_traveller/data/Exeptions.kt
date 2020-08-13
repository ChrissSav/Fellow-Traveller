package gr.fellow.fellow_traveller.data


open class BaseApiException(
    open var code: Int
) : Exception()
