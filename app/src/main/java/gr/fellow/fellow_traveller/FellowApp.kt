package gr.fellow.fellow_traveller

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.SharedPreferences
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class FellowApp : Application() {

    companion object {
        const val CHANNEL_TRIPS_ID = "CHANNEL_TRIPS_ID"
    }


    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()

        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        val alarmSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel1 = NotificationChannel(
                CHANNEL_TRIPS_ID,
                "Ειδοποιήσεις σχετικά με ταξίδια",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel1.description = "Ειδοποιήσεις σχετικά με ταξίδια"
            channel1.enableVibration(true)
            channel1.setSound(alarmSound, audioAttributes);
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel1)
        }
    }
}