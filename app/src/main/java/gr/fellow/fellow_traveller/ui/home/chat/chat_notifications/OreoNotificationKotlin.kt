package gr.fellow.fellow_traveller.ui.home.chat.chat_notifications

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.net.Uri
import android.os.Build


class OreoNotificationKotlin(base: Context?) : ContextWrapper(base) {

    companion object {
        private const val CHANNEL_ID = "gr.fellow.fellow_traveller"
        private const val CHANNEL_NAME = "fellow_traveller"
    }

    private var notificationManager: NotificationManager? = null


    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
        channel.enableLights(true)
        channel.enableVibration(true)
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        getManager()?.createNotificationChannel(channel)
    }

    fun getManager(): NotificationManager? {
        if (notificationManager == null) {
            notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        }
        return notificationManager
    }

    @TargetApi(Build.VERSION_CODES.O)
    fun getOreoNotification(title: String?, body: String?, pendingIntent: PendingIntent?, soundUri: Uri?, icon: String): Notification.Builder? {
        return Notification.Builder(applicationContext, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(icon.toInt())
            .setSound(soundUri)
            .setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
    }
}