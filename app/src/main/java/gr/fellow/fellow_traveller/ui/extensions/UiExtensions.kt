package gr.fellow.fellow_traveller.ui.extensions

import android.app.Activity
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import gr.fellow.fellow_traveller.utils.RESENT_EMAIL
import gr.fellow.fellow_traveller.utils.set
import kotlin.math.round
import kotlin.reflect.KClass


fun Activity.createAlerter(msg: String) {
    var icon = R.drawable.ic_exclamation_mark
    if (this.resources.getString(R.string.ERROR_INTERNET_CONNECTION) == msg)
        icon = R.drawable.ic_no_wifi
    Alerter.create(this)
        .setTitle(getString(R.string.warning))
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
        .setTitle(getString(R.string.warning))
        .setText(msg)
        .setIcon(icon)
        .setBackgroundColorRes(color)
        .setDuration(1800)
        .enableSwipeToDismiss() //seems to not work well with OnClickListener
        .show()
}


fun Activity.pasteTextFromClipboard(): String? {
    val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    return clipboardManager.primaryClip?.getItemAt(0)?.text?.toString()
}

fun Activity.openMessenger(url: String) {
    val uriUrl: Uri = Uri.parse(getString(R.string.messenger_link, url))
    val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
    startActivity(launchBrowser)
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
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    startActivity(intent)
    finishAffinity()
}


fun Activity.openUrl(url: String) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(browserIntent)
}

fun Activity.openGoogleMaps(trip: TripSearch) {
    val uri = "https://www.google.com/maps/dir/${trip.destFrom.title}/${trip.destTo.title}/"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    startActivity(Intent.createChooser(intent, getString(R.string.select_application)))

}

fun Activity.openGoogleMaps(trip: TripInvolved) {
    val uri = "https://www.google.com/maps/dir/${trip.destFrom.title}/${trip.destTo.title}/"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    startActivity(Intent.createChooser(intent, getString(R.string.select_application)))
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
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
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


fun getResentEmail(sharedPreferences: SharedPreferences): String? {
    return sharedPreferences.getString(RESENT_EMAIL, "")
}

fun setResentEmail(sharedPreferences: SharedPreferences, email: String?) {
    sharedPreferences[RESENT_EMAIL] = email
}


fun Fragment.createAlerter(msg: String) {
    var icon = R.drawable.ic_exclamation_mark
    if (this.resources.getString(R.string.ERROR_INTERNET_CONNECTION) == msg)
        icon = R.drawable.ic_no_wifi
    Alerter.create(activity)
        .setTitle(getString(R.string.warning))
        .setText(msg)
        .setIcon(icon)
        .setBackgroundColorRes(R.color.colorPrimary)
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


/** ____________OTHERS_____________________________ */


fun ImageView.loadImageFromUrl(url: String?) {
    if (!url.isNullOrBlank())
        Glide.with(this)
            .load(url)
            .into(this)
    else
        setImageDrawable(null)
}

fun getRandomImage(): String {
    val cities = mutableListOf<String>()
    cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fcity-1.jpg?alt=media&token=897e0c15-6b66-461f-a50a-a08d4715fd2e")
    cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fcity-10.jpg?alt=media&token=2783d8b8-aa6c-4c0a-835f-4bf9ae7be4ed")
    cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fcity-11.jpg?alt=media&token=9d3f9c0b-b601-4dcd-a85d-a19667e366db")
    cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fcity-12.jpg?alt=media&token=9608bd94-d866-4153-a7f6-3b57a4cf556c")
    cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fcity-13.jpg?alt=media&token=1c5dfa90-5f89-4fd4-adda-61c0d83ecb48")
    cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fcity-14.jpg?alt=media&token=c231fe8f-098e-4237-a930-0f5fe2a3546e")
    cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fcity-15.jpg?alt=media&token=abe03210-345f-4c43-8d7e-e0e2a808c451")
    cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fcity-16.jpg?alt=media&token=e4fc2860-78c6-46c9-a9b4-008b91daf0bb")
    cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fcity-17.jpg?alt=media&token=f7325c80-80a6-49ff-ac72-eadd32ce2c86")
    cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fcity-18.jpg?alt=media&token=581efc48-5ead-4f79-905f-aa4e38d6ffc4")
    cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fcity-19.jpg?alt=media&token=34934ca4-aa42-481b-91de-9c108b35a307")
    cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fcity-21.jpg?alt=media&token=33369ccb-b560-4df5-96c9-24672f477c48")
    cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fcity-22.jpg?alt=media&token=4a3de3e5-4952-4305-9d69-8b77ecb8fb69")
    cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fcity-23.jpg?alt=media&token=1d072a15-2322-4091-a708-23d91a752481")
    cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fcity-20.jpg?alt=media&token=74a402d7-46e8-4dc0-84bf-b9c60825ee3c")
    cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fcity-3.jpg?alt=media&token=a191a082-121a-4cc8-8a2e-5c428518e21c")
    cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fcity-4.jpg?alt=media&token=857b5e18-cdfb-4785-946a-ba31f7e56725")
    cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fcity-5.jpg?alt=media&token=61e423bd-bb13-4a2a-8a3e-feb8efb871ea")
    cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fcity-6.jpg?alt=media&token=7095512d-679b-4b4b-8e5d-e12f075caa5f")
    cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fcity-7.jpg?alt=media&token=d9fd1a43-2363-4cbe-b681-b6bf405e0f52")
    cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fcity-8.jpg?alt=media&token=a6be0e1d-db32-43b2-9817-6dcafa18b90a")
    cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fcity-9.jpg?alt=media&token=f0488dfb-0df2-49a2-b816-d34267ac9b45")
    cities.add("https://firebasestorage.googleapis.com/v0/b/fellow-traveller-firebase.appspot.com/o/userImages%2Fcity-2.jpg?alt=media&token=cc33af7d-09e0-41af-b75a-698e18130e56")

    return cities.random()
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

fun Float.round(decimals: Int): Float {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return (round(this * multiplier) / multiplier).toFloat()
}
