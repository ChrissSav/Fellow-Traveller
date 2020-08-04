package gr.fellow.fellow_traveller

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FellowApp : Application(){
    override fun onCreate() {
        super.onCreate()
    }
}