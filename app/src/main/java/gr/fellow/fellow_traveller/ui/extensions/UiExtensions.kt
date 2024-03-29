package gr.fellow.fellow_traveller.ui.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerFrameLayout
import com.tapadoo.alerter.Alerter
import gr.fellow.fellow_traveller.BuildConfig
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.domain.trip.Destination
import gr.fellow.fellow_traveller.domain.trip.TripInvolved
import gr.fellow.fellow_traveller.domain.trip.TripSearch
import gr.fellow.fellow_traveller.ui.views.FellowEditTextNew
import gr.fellow.fellow_traveller.ui.views.FellowEditTextNewOnClickListener
import gr.fellow.fellow_traveller.utils.PLACE_TITLE_SPLIT_CHAR
import gr.fellow.fellow_traveller.utils.RESENT_EMAIL
import gr.fellow.fellow_traveller.utils.set
import kotlin.reflect.KClass


fun Activity.createAlerter(msg: String) {
    var icon = R.drawable.ic_exclamation_mark
    if (this.resources.getString(R.string.ERROR_INTERNET_CONNECTION) == msg)
        icon = R.drawable.ic_no_wifi
    Alerter.create(this)
        .setTitle(getString(R.string.warning))
        .setText(msg)
        .setIcon(icon)
        .setBackgroundColorRes(R.color.black_new)
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


fun Activity.openGoogleMaps(destination: Destination) {
    val uri = "https://maps.google.com/?q=${destination.title.replace("$$", " ").replace(" ", "+")}"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    startActivity(Intent.createChooser(intent, getString(R.string.select_application)))
}


fun Activity.openGoogleMaps(trip: TripInvolved) {
    val uri = "https://www.google.com/maps/dir/${trip.destFrom.fullTitle}/${trip.destTo.fullTitle}/"
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
    intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
    startActivityForResult(intent, code)
}


fun Fragment.startActivityForResultWithFade(activity: KClass<out Activity>, code: Int, bundle: Bundle? = null) {
    val intent = Intent(this.context, activity.java)
    bundle?.let {
        intent.putExtras(it)
    }
    startActivityForResult(intent, code)
    this.activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
}

fun Activity.startActivityForResultWithFade(activity: KClass<out Activity>, code: Int, bundle: Bundle?) {
    val intent = Intent(this, activity.java)
    bundle?.let {
        intent.putExtras(it)
    }
    startActivityForResult(intent, code)
    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
}

fun Activity.startActivityForResultWithFade(activity: KClass<out Activity>, code: Int) {
    val intent = Intent(this, activity.java)
    startActivityForResult(intent, code)
    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
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
    try {
        view?.let {
            return Navigation.findNavController(it)
        }
        return null
    } catch (e: java.lang.Exception) {
        return null
    }
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
    if (url != null) {
        Glide.with(this.context)
            .load(url)
            .into(this)
    } else {
        Glide.with(this.context)
            .load(R.drawable.ic_boy)
            .into(this)
    }
}


fun ImageView.loadDestination(destination: Destination) {
    Glide.with(this.context)
        .load(BuildConfig.FELLOW_API_URL + "images/destinations?latitude=${destination.latitude}&longitude=${destination.longitude}&height=720&width=720")
        .into(this)
}

fun ImageView.loadDestinationImage(destination: Destination, color: String) {
    Glide.with(this.context)
        .load(BuildConfig.FELLOW_API_URL + "images/destination?latitude=${destination.latitude}&longitude=${destination.longitude}&height=720&width=720&color=${color.substring(3)}")
        .into(this)
}

fun ImageView.loadDirectionsImage(trip: TripInvolved, color: String) {
    Glide.with(this.context)
        .load(
            BuildConfig.FELLOW_API_URL + "images/directions?latitudeStart=${trip.destFrom.latitude}&longitudeStart=${trip.destFrom.longitude}" +
                    "&latitudeEnd=${trip.destTo.latitude}&longitudeEnd=${trip.destTo.longitude}" +
                    "&height=720&width=720&color=%23${color.substring(3)}"
        )
        .into(this)
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


fun TextView.setDrawableTint(color: Int) {
    compoundDrawablesRelative.forEach {
        if (it != null) {
            it.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(this.context, color), PorterDuff.Mode.SRC_IN)
        }

    }
}

fun ShimmerFrameLayout.startShimmerWithVisibility() {
    visibility = View.VISIBLE
    startShimmer()
}

fun ShimmerFrameLayout.stopShimmerWithVisibility() {
    stopShimmer()
    visibility = View.GONE
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


fun TextView.setTextBackTint(mText: Int, color: Int) {
    if (text.toString() != resources.getString(mText)) {

        val anim = AlphaAnimation(1.0f, 0.5f)
        anim.duration = 150
        anim.repeatCount = 1
        anim.repeatMode = Animation.REVERSE

        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation?) {}
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {
                text = resources.getString(mText)
                val drawable = background as GradientDrawable
                drawable.setStroke(3, ContextCompat.getColor(context, color))
                setTextColor(ContextCompat.getColor(context, color))
            }
        })

        startAnimation(anim)

    }
}

fun View.goneAnim() {
    if (visibility != View.GONE)
        animate().alpha(1f).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                visibility = View.GONE
            }
        })

}

fun View.visibleAnim() {
    if (visibility != View.VISIBLE)
        animate().alpha(1f).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                visibility = View.VISIBLE
            }
        })

}


fun TextView.setSpanTextColor(vararg pairs: Pair<String, Int>) {

    val ssb = SpannableStringBuilder("")

    pairs.forEach { current ->
        val color = ForegroundColorSpan(ContextCompat.getColor(context, current.second))
        val tempSsb = SpannableStringBuilder(current.first)
        tempSsb.setSpan(color, 0, current.first.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        ssb.append(tempSsb)
    }

    this.setText(ssb, TextView.BufferType.SPANNABLE)
}


fun TextView.setSpanTextStyle(vararg pairs: Pair<String, Int>) {
    val ssb = SpannableStringBuilder("")
    pairs.forEach { current ->
        val style = StyleSpan(current.second)
        val tempSsb = SpannableStringBuilder(current.first)
        tempSsb.setSpan(style, 0, current.first.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        ssb.append(tempSsb)
    }
    this.setText(ssb, TextView.BufferType.SPANNABLE)
}

fun FellowEditTextNew.setSpanTextStyle(vararg pairs: Pair<String, Int>) {
    val ssb = SpannableStringBuilder("")
    pairs.forEach { current ->
        val style = StyleSpan(current.second)
        val tempSsb = SpannableStringBuilder(current.first)
        tempSsb.setSpan(style, 0, current.first.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        ssb.append(tempSsb)
    }
    this.editText.setText(ssb, TextView.BufferType.SPANNABLE)
}


fun TextView.setDestination(destination: Destination) {
    if (destination.title.contains(PLACE_TITLE_SPLIT_CHAR)) {
        setSpanTextStyle(
            Pair(destination.title.split(PLACE_TITLE_SPLIT_CHAR)[0], R.style.header_4_new),
            Pair(destination.title.split(PLACE_TITLE_SPLIT_CHAR)[1], R.style.paragraph_3_new)
        )
    } else {
        text = destination.title
    }
}


fun <T : Any> FellowEditTextNew.onClickListener(function: () -> T) {
    fellowEditTextNewOnClickListener = object : FellowEditTextNewOnClickListener {
        override fun onClick() {
            function.invoke()
        }
    }
}



