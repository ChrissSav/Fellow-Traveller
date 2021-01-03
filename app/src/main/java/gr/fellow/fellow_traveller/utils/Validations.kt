package gr.fellow.fellow_traveller.utils

import android.util.Patterns
import java.util.regex.Matcher
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


fun isValidRegex(text: String, regex: String): Boolean {
    val regexPattern = Pattern.compile(regex)
    return regexPattern.matcher(text).matches()
}

fun CharSequence.hasLowerCase(): Boolean {
    val pattern: Pattern = Pattern.compile("[a-z]")
    val hasLowerCase: Matcher = pattern.matcher(this)
    return hasLowerCase.find()
}

fun CharSequence.hasUpperCase(): Boolean {
    val pattern: Pattern = Pattern.compile("[A-Z]")
    val hasUpperCase: Matcher = pattern.matcher(this)
    return hasUpperCase.find()
}

fun CharSequence.hasDigit(): Boolean {
    val pattern: Pattern = Pattern.compile("[0-9]")
    val hasDigit: Matcher = pattern.matcher(this)
    return hasDigit.find()
}

fun CharSequence.hasSpecialChar(): Boolean {
    val pattern: Pattern = Pattern.compile("[!@#$%^&*()_=+{}/.<>|\\[\\]~-]")
    val hasSpecialChar: Matcher = pattern.matcher(this)
    return hasSpecialChar.find()
}