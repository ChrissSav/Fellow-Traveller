package gr.fellow.fellow_traveller.ui.home.chat.chat_notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.ui.home.HomeActivity
import gr.fellow.fellow_traveller.ui.home.chat.models.Conversation
import kotlin.random.Random

private const val CHANNEL_ID = "fellowChatChannel"

class FirebaseService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)


        //Intent when click on notification
        // val intent = Intent(this, ChatFragment::class.java)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationID = Random.nextInt()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }


        val pendingIntent = NavDeepLinkBuilder(this)
            .setGraph(R.navigation.home_nav_graph)
            .setComponentName(HomeActivity::class.java)
            .setDestination(R.id.chatFragment)
            .setArguments(
                bundleOf(
                    "conversationItem" to Conversation("sdsafs", "Yes", "dsada", 10060606, "hdghdffx", true)
                )
            )
            .createPendingIntent()

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(message.data["title"])
            .setContentText(message.data["message"])
            .setSmallIcon(R.drawable.ic_messenger)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(notificationID, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channelName = "fellowChannel"
        val channel = NotificationChannel(CHANNEL_ID, channelName, IMPORTANCE_HIGH).apply {
            description = "Fellow channel for notifications"
            enableLights(true)
            lightColor = Color.GREEN

        }
        notificationManager.createNotificationChannel(channel)

    }
}