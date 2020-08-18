package gr.fellow.fellow_traveller

import android.app.Application
import android.content.SharedPreferences
import dagger.hilt.android.HiltAndroidApp
import gr.fellow.fellow_traveller.utils.PREFS_AUTH_TOKEN
import javax.inject.Inject

@HiltAndroidApp
class FellowApp : Application() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()

        val test = sharedPreferences.getString(PREFS_AUTH_TOKEN, "") + " "
        // createToast(this, test)
    }
}