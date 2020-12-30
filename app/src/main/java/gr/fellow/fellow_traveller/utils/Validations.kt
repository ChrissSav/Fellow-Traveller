package gr.fellow.fellow_traveller.utils

import android.util.Patterns
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.domain.StrengthLevel
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


fun calculateStrength(password: CharSequence): Pair<Int, StrengthLevel> {

    val lowerCase = if (password.hasLowerCase()) 1 else 0
    val upperCase = if (password.hasUpperCase()) 1 else 0
    val digit = if (password.hasDigit()) 1 else 0
    val specialChar = if (password.hasSpecialChar()) 1 else 0

    if (password.length in 0..7) {
        return Pair(R.color.weak, StrengthLevel.WEAK)
    } else if (password.length in 8..10) {
        if (lowerCase == 1 || upperCase == 1 || digit == 1 || specialChar == 1) {
            return Pair(R.color.medium, StrengthLevel.MEDIUM)
        }
    } else if (password.length in 11..16) {
        if (lowerCase == 1 || upperCase == 1 || digit == 1 || specialChar == 1) {
            if (lowerCase == 1 && upperCase == 1) {
                return Pair(R.color.strong, StrengthLevel.STRONG)
            }
        }
    } else if (password.length > 16) {
        if (lowerCase == 1 && upperCase == 1 && digit == 1 && specialChar == 1) {
            return Pair(R.color.bulletproof, StrengthLevel.BULLETPROOF)
        }
    }
    return Pair(R.color.weak, StrengthLevel.WEAK)
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