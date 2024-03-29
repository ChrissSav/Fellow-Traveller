package gr.fellow.fellow_traveller.domain

import gr.fellow.fellow_traveller.R


data class ErrorMessage(
    val internal: Boolean = false,
    val messageId: Int = R.string.ERROR_SOMETHING_WRONG,
    val message: String = ""
)

fun internalError(messageId: Int): ErrorMessage {
    return ErrorMessage(internal = true, messageId = messageId)
}

fun externalError(message: String): ErrorMessage {
    return ErrorMessage(internal = false, message = message)
}