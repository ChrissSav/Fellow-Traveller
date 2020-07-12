package com.example.fellowtravellerbeta.utils

import android.util.Patterns
import android.view.View
import com.example.fellowtravellerbeta.R
import com.google.android.material.snackbar.Snackbar
import java.io.IOException
import java.sql.Date
import java.text.ParseException
import java.text.SimpleDateFormat
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

fun dateTimeToTimestamp(date: String, time: String): Long {
    val p = "0".toLong()
    try {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val parsedDate = dateFormat.parse("$date $time") as Date
        return parsedDate.time / 1000
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return p
}


fun currentTimeStamp(): Long? {
    return System.currentTimeMillis() / 1000L
}

fun isInternetAvailable(): Boolean {
    val command = "ping -c 1 google.com"
    return try {
        Runtime.getRuntime().exec(command).waitFor() == 0
    } catch (e: InterruptedException) {
        e.printStackTrace()
        false
    } catch (e: IOException) {
        e.printStackTrace()
        false
    }
}

fun isValidPlate(plate: String): Boolean {
    val regexPattern = Pattern.compile("^[ΑΒΕΖΗΙΚΜΝΟΡΤΥΧ]{3}-[0-9]{4}$")
    return regexPattern.matcher(plate).matches()
}

fun isValidEmail(email: String): Boolean {
    return  Patterns.EMAIL_ADDRESS.matcher(email).matches()
}