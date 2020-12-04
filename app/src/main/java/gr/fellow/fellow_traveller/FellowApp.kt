package gr.fellow.fellow_traveller

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.SharedPreferences
import android.os.Build
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class FellowApp : Application() {

    companion object {
        const val CHANNEL_1_ID = "CHANNEL_1_ID"
    }

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()

        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel1 = NotificationChannel(
                CHANNEL_1_ID,
                "Channel 1",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel1.description = "This is Channel 1"

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel1)
        }
    }
}