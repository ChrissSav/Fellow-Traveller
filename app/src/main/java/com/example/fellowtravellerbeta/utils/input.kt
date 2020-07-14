package com.example.fellowtravellerbeta.utils

import android.util.Patterns
import android.view.View
import com.example.fellowtravellerbeta.R
import com.example.fellowtravellerbeta.data.network.fellow.response.DetailResponse
import com.example.fellowtravellerbeta.data.network.fellow.response.ErrorResponseModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.JsonParser
import retrofit2.Response
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
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


fun currentTimeStamp(): Long {
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
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun getModelFromResponseErrorBody(response: Response<*>): ErrorResponseModel {
    val gson = Gson()
    val detail = DetailResponse(1000, "")
    var errorResponseModel = ErrorResponseModel("", detail)
    try {
        val mJsonString = response.errorBody()?.string()
        val parser = JsonParser()
        val mJson = parser.parse(mJsonString)
        errorResponseModel =
            gson.fromJson<ErrorResponseModel>(mJson, ErrorResponseModel::class.java)

    } catch (e: Exception) {
//        val detailModel = DetailModel(1000)
//        errorResponseModel.setDetail(detailModel)
    }
    return errorResponseModel
}


fun validateDateTimeDiffer(date: String, time: String, timeDiffer: Int): Boolean {
    val timestamp = currentTimeStamp()
    if ((dateTimeToTimestamp(date, time) - timestamp) >= timeDiffer) {
        return true
    }
    return false
}

