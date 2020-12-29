package gr.fellow.fellow_traveller.utils

import android.annotation.SuppressLint
import android.content.Context
import android.text.format.DateFormat
import gr.fellow.fellow_traveller.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun dateTimeToTimestamp(date: String, time: String): Long {
    val p = 0L
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


fun getDateFromTimestamp(timestamp: Long, format: String): String {
    val calendar = Calendar.getInstance(Locale.ENGLISH)
    calendar.timeInMillis = timestamp * 1000L
    return DateFormat.format(format, calendar).toString()
}

fun getTimeFromTimestamp(timestamp: Long): String {
    val calendar = Calendar.getInstance(Locale.ENGLISH)
    calendar.timeInMillis = timestamp * 1000L
    return DateFormat.format("hh:mm aaa", calendar).toString()
}

@SuppressLint("SimpleDateFormat")
fun convertTimestampToFormat(time: Long, format: String): String {
    val date = if (time.toString().length == 10)
        Date(time * 1000L)
    else
        Date(time)
    return SimpleDateFormat(format).format(date)
}

//Set minutes ago
fun getTimeTiDisplay(mTimestamp: Long, context: Context): String {
    var timestamp = mTimestamp
    if (timestamp.toString().length > 10)
        timestamp /= 1000
    val t = currentTimeStamp() - timestamp
    if (t <= 3600) {
        return if ((t / 60).toInt() == 1)
            context.getString(R.string.minute_ago)
        //"${(t / 60).toInt()} λεπτό"
        else
            context.getString(R.string.minutes_ago, ((t / 60).toInt()).toString())
        //"${(t / 60).toInt()} λεπτά"
    } else if (t <= 3600 * 24)
        return if ((t / 3600).toInt() == 1)
            context.getString(R.string.hour_ago)
        //"${(t / 3600).toInt()} ώρα"
        else
            context.getString(R.string.hours_ago, ((t / 3600).toInt()).toString())
    // "${(t / 3600).toInt()} ώρες"
    else if ((t / (3600 * 24)) <= 7)
        return if ((t / (3600 * 24)).toInt() == 1)
            context.getString(R.string.day_ago)
        //"${(t / (3600 * 24)).toInt()} μέρα"
        else
            context.getString(R.string.days_ago, ((t / (3600 * 24)).toInt()).toString())
    else
        return getDateFromTimestamp(timestamp, "d MMM yyyy")
    //  "${(t / (3600 * 24)).toInt()} μέρες"

    //return test
}
