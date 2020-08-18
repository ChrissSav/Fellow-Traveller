package gr.fellow.fellow_traveller.ui

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import gr.fellow.fellow_traveller.R
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun createSnackBar(view: View, msg: String) {
    Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
        .setActionTextColor(view.context.resources.getColor(R.color.colorPrimary))
        .show()

}

fun Activity.createSnackBar(msg: String) {
    Snackbar.make(this.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG)
        .setActionTextColor(this.resources.getColor(R.color.colorPrimary))
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

fun createToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

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
