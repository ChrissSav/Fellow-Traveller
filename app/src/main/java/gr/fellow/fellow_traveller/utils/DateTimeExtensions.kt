package gr.fellow.fellow_traveller.utils

import android.text.format.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


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


fun getDateFromTimestamp(timestamp: Long): String {
    val calendar = Calendar.getInstance(Locale.ENGLISH)
    calendar.timeInMillis = timestamp * 1000L
    return DateFormat.format("EEE, d MMM", calendar).toString()
}

fun getTimeFromTimestamp(timestamp: Long): String {
    val calendar = Calendar.getInstance(Locale.ENGLISH)
    calendar.timeInMillis = timestamp * 1000L
    return DateFormat.format("hh:mm aaa", calendar).toString()
}