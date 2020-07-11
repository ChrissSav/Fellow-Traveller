package com.example.fellowtravellerbeta.utils

import android.view.View
import com.example.fellowtravellerbeta.R
import com.google.android.material.snackbar.Snackbar
import java.util.regex.Pattern

fun isValidPhone(phone: String): Boolean {
    val regexPattern = Pattern.compile("^[6][9][0-9]{8}$")
    return regexPattern.matcher(phone).matches()
}

fun createSnackBar(view: View, msg: String) {
    Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
        .setActionTextColor(view.context.resources.getColor(R.color.colorPrimary))
        .show()

}