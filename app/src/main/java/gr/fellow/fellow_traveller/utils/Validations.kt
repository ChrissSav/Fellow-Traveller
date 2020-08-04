package gr.fellow.fellow_traveller.utils

import android.util.Patterns
import gr.fellow.fellow_traveller.ui.currentTimeStamp
import gr.fellow.fellow_traveller.ui.dateTimeToTimestamp
import java.util.regex.Pattern

fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}


fun validateDateTimeDiffer(date: String, time: String, timeDiffer: Int): Boolean {
    val timestamp = currentTimeStamp()
    if ((dateTimeToTimestamp(date, time) - timestamp) >= timeDiffer) {
        return true
    }
    return false
}

fun isValidPhone(phone: String): Boolean {
    val regexPattern = Pattern.compile("^[6][9][0-9]{8}$")
    return regexPattern.matcher(phone).matches()
}


fun isValidPlate(plate: String): Boolean {
    val regexPattern = Pattern.compile("^[ΑΒΕΖΗΙΚΜΝΟΡΤΥΧ]{3}-[0-9]{4}$")
    return regexPattern.matcher(plate).matches()
}