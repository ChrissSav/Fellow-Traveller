package gr.fellow.fellow_traveller.ui.extensions

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.AnimatedVectorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.bumptech.glide.Glide
import com.tapadoo.alerter.Alerter
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.domain.trip.TripSearch
import kotlin.reflect.KClass


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


fun Activity.openActivityWithFade(intent: Intent) {
    startActivity(intent)
    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    finish()
}

fun Activity.createToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}


fun Activity.hideKeyboard() {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
}


fun Activity.startActivityWithBundle(activity: KClass<out Activity>, bundle: Bundle) {
    val intent = Intent(this, activity.java)
    intent.putExtras(bundle)
    startActivity(intent)
}

fun Activity.startActivityWithFade(activity: KClass<out Activity>, bundle: Bundle?) {
    val intent = Intent(this, activity.java)
    bundle?.let {
        intent.putExtras(it)
    }
    startActivity(intent)
    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
}

fun Activity.startActivityClearStack(activity: KClass<out Activity>) {
    val intent = Intent(this, activity.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(intent)
    finishAffinity()

}

fun Activity.openGoogleMaps(trip: TripSearch) {
    val uri = "http://maps.google.com/maps?f=d&hl=en&saddr=${trip.destFrom.latitude},${trip.destFrom.longitude}&daddr=${trip.destTo.latitude},${trip.destTo.longitude}&mode=d"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    startActivity(Intent.createChooser(intent, "Select an application"))

}

fun Activity.openGoogleMaps(trip: TripInvolved) {
    val uri = "http://maps.google.com/maps?f=d&hl=en&saddr=${trip.destFrom.latitude},${trip.destFrom.longitude}&daddr=${trip.destTo.latitude},${trip.destTo.longitude}&mode=d"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    startActivity(Intent.createChooser(intent, "Select an application"))
}


/** ____________FRAGMENTS_____________________________ */

fun Fragment.startActivity(activity: KClass<out Activity>) {
    val intent = Intent(this.context, activity.java)
    startActivity(intent)
}

fun Fragment.startActivityToLeft(intent: Intent, code: Int) {
    startActivityForResult(intent, code)
    this.activity?.overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
}


fun Fragment.startActivityClearStack(activityTemp: KClass<out Activity>) {
    val intent = Intent(this.context, activityTemp.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(intent)
    activity?.finishAffinity()
}


fun Fragment.startActivityForResult(activity: KClass<out Activity>, code: Int, bundle: Bundle?) {
    val intent = Intent(this.context, activity.java)
    bundle?.let {
        intent.putExtras(it)
    }
    startActivityForResult(intent, code)
}


fun Fragment.startActivityForResultWithFade(activity: KClass<out Activity>, code: Int) {
    val intent = Intent(this.context, activity.java)
    startActivityForResult(intent, code)
    this.activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
}


fun Fragment.startActivityForResultWithFade(intent: Intent, code: Int) {
    startActivityForResult(intent, code)
    this.activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
}

fun Fragment.onBackPressed() {
    activity?.onBackPressed()
}

fun Fragment.createToast(msg: String) {
    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.findNavController(): NavController? {
    view?.let {
        return Navigation.findNavController(it)
    }
    return null
}

fun Fragment.hideKeyboard() {
    val imm = this.activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.view?.rootView?.windowToken, 0)
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


fun Fragment.createAlerterInfo(msg: String, color: Int) {
    Alerter.create(activity)
        .setTitle("Ενημέρωση")
        .setText(msg)
        .setIcon(R.drawable.ic_lock)
        .setBackgroundColorRes(color)
        .setDuration(1800)
        .enableSwipeToDismiss() //seems to not work well with OnClickListener
        .show()
}


/***  NAV GRAPH***/

fun NavController.navigateWithFade(actionId: Int) {
    val navBuilder = NavOptions.Builder()
    navBuilder.setEnterAnim(R.anim.fade_in)
    navBuilder.setExitAnim(R.anim.fade_out)
    navBuilder.setPopEnterAnim(R.anim.fade_in)
    navBuilder.setPopExitAnim(R.anim.fade_out)
    this.navigate(actionId, null, navBuilder.build())
}

fun NavController.bottomNav(actionId: Int) {
    try {
        getBackStackEntry(actionId)
        popBackStack(actionId, false)
    } catch (e: Exception) {
        val navBuilder = NavOptions.Builder()
        navBuilder.setEnterAnim(R.anim.fade_in)
        navBuilder.setExitAnim(R.anim.fade_out)
        navBuilder.setPopEnterAnim(R.anim.fade_in)
        navBuilder.setPopExitAnim(R.anim.fade_out)
        this.navigate(actionId, null, navBuilder.build())
    }

}

fun NavController.navigateWithAnimation(actionId: Int, bundle: Bundle? = null) {

    val navBuilder = NavOptions.Builder()

    navBuilder.setEnterAnim(R.anim.enter_from_right)
    navBuilder.setExitAnim(R.anim.exit_to_left)
    navBuilder.setPopEnterAnim(R.anim.enter_from_left)
    navBuilder.setPopExitAnim(R.anim.exit_to_right)
    this.navigate(actionId, bundle, navBuilder.build())
}

/** ____________OTHERS_____________________________ */


fun ImageView.loadImageFromUrl(url: String?) {
    if (!url.isNullOrBlank())
        Glide.with(this)
            .load(url)
            .into(this)
    else
        setImageDrawable(null)
}


fun ImageView.startAnimation() {
    if (drawable is AnimatedVectorDrawableCompat) {
        (drawable as AnimatedVectorDrawableCompat).start()
    } else if (drawable is AnimatedVectorDrawable) {
        (drawable as AnimatedVectorDrawable).start()
    }
}

fun ImageButton.startAnimation() {
    if (drawable is AnimatedVectorDrawableCompat) {
        (drawable as AnimatedVectorDrawableCompat).start()
    } else if (drawable is AnimatedVectorDrawable) {
        (drawable as AnimatedVectorDrawable).start()
    }
}


fun postDelay(time: Long, function: () -> Unit) {
    /*Timer().schedule(object : TimerTask() {
        override fun run() {
            function.invoke()
        }
    }, time)*/

    val handler = Handler()
    handler.postDelayed({ function.invoke() }, time)
}


fun TextView.setTextHtml(text: String) {
    this.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
}


fun CharSequence.getString(): String? {
    return if (this.isEmpty()) null else this.toString().trim()
}

fun CharSequence.getLength(): Int {
    return if (this.isEmpty()) 0 else this.toString().length
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

