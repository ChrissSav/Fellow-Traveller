package gr.fellow.fellow_traveller

import android.app.Application
import android.content.SharedPreferences
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class FellowApp : Application() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

}