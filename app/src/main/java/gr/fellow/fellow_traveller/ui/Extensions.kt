package gr.fellow.fellow_traveller.ui

import android.app.Activity
import android.content.res.Resources
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.tapadoo.alerter.Alerter
import gr.fellow.fellow_traveller.R
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun Activity.createAlerter(msg: String) {
    var icon = R.drawable.ic_exclamation_mark
    if (this.resources.getString(R.string.ERROR_INTERNET_CONNECTION) == msg)
        icon = R.drawable.ic_no_wifi
    Alerter.create(this)
        .setTitle("Προσοχή")
        .setText(msg)
        .setIcon(icon)
        .setBackgroundColorRes(R.color.colorPrimary)
        .setDuration(1800)
        .enableSwipeToDismiss() //seems to not work well with OnClickListener
        .show()
}


fun Activity.createAlerter(msg: String, color: Int) {
    var icon = R.drawable.ic_exclamation_mark
    if (this.resources.getString(R.string.ERROR_INTERNET_CONNECTION) == msg)
        icon = R.drawable.ic_no_wifi
    Alerter.create(this)
        .setTitle("Προσοχή")
        .setText(msg)
        .setIcon(icon)
        .setBackgroundColorRes(color)
        .setDuration(1800)
        .enableSwipeToDismiss() //seems to not work well with OnClickListener
        .show()
}


fun Activity.createToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.createToast(msg: String) {
    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
}


fun Fragment.createAlerter(msg: String) {
    var icon = R.drawable.ic_exclamation_mark
    if (this.resources.getString(R.string.ERROR_INTERNET_CONNECTION) == msg)
        icon = R.drawable.ic_no_wifi
    Alerter.create(activity)
        .setTitle("Προσοχή")
        .setText(msg)
        .setIcon(icon)
        .setBackgroundColorRes(R.color.colorPrimary)
        .setDuration(1800)
        .enableSwipeToDismiss() //seems to not work well with OnClickListener
        .show()
}

fun Activity.hideKeyboard() {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
}


fun Fragment.hideKeyboard() {
    val imm = this.activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.view?.rootView?.windowToken, 0)
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


fun ImageView.loadImageFromUrl(url: String?) {
    if (!url.isNullOrBlank())
        Glide.with(this)
            .load(url)
            .into(this);
}

val Int.toPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Int.toDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Float.toDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Float.toPx: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Double.toDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Double.toPx: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()


