package gr.fellow.fellow_traveller.ui.extensions

import android.content.res.Resources
import androidx.core.text.HtmlCompat
import androidx.lifecycle.MutableLiveData
import gr.fellow.fellow_traveller.domain.trip.TripInvolved


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


fun String.getTextHtml(): String = HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()


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
    return (kotlin.math.round(this * multiplier) / multiplier).toFloat()
}


fun MutableList<TripInvolved>.addOrReplace(item: TripInvolved) {
    val index = indexOfFirst { it.id == item.id }
    if (index == -1)
        add(item)
    else
        this[index] = item
}

fun <T> MutableLiveData<MutableList<T>>.toMutableListSafe(): MutableList<T> {
    return value ?: mutableListOf()
}

fun MutableLiveData<MutableList<TripInvolved>>.addOrReplace(item: TripInvolved) {
    val temp = value ?: mutableListOf()
    temp.addOrReplace(item)
    value = temp
}