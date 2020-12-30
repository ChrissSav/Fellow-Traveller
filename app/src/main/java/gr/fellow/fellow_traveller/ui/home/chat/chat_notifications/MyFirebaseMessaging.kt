package gr.fellow.fellow_traveller.ui.home.chat.chat_notifications

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import gr.fellow.fellow_traveller.ui.home.chat.ChatFragment


class MyFirebaseMessaging : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

        //Get users id
        var myId = "sdags"

        val sented: String? = p0.data.get("sented")
        if (myId != null && sented == myId) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                sendOreoNotification(p0)
            } else {
                sendNotification(p0)
            }
        }
    }

    private fun sendOreoNotification(remoteMessage: RemoteMessage) {
        val user = remoteMessage.data["user"]
        val icon = remoteMessage.data["icon"]
        val title = remoteMessage.data["title"]
        val body = remoteMessage.data["body"]

        val notification = remoteMessage.notification
        val j = user!!.replace("[\\D]".toRegex(), "").toInt()

        /* Is this okey???? */
        /* Is this okey???? */
        /* Is this okey???? */
        val intent = Intent(this, ChatFragment::class.java)
        val bundle = Bundle()

        bundle.putString("groupId", user)
        intent.putExtras(bundle)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, j, intent, PendingIntent.FLAG_ONE_SHOT)
        val defaultSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val oreoNotification = OreoNotification(this)
        val builder = oreoNotification.getOreoNotification(title, body, pendingIntent, defaultSound, icon)

        var i = 0
        if (j > 0) {
            i = j
        }
        oreoNotification.manager.notify(i, builder.build())


    }

    @SuppressLint("WrongConstant")
    private fun sendNotification(remoteMessage: RemoteMessage) {

        val user = remoteMessage.data["user"]
        val icon = remoteMessage.data["icon"]
        val title = remoteMessage.data["title"]
        val body = remoteMessage.data["body"]

        val notification = remoteMessage.notification
        val j = user!!.replace("[\\D]".toRegex(), "").toInt()

        /* Is this okey???? */
        /* Is this okey???? */
        /* Is this okey???? */
        val intent = Intent(this, ChatFragment::class.java)
        val bundle = Bundle()

        bundle.putString("groupId", user)
        intent.putExtras(bundle)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, j, intent, PendingIntent.FLAG_ONE_SHOT)
        val defaultSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)


        val notification2 = NotificationCompat.Builder(this)
            .setSmallIcon(Integer.parseInt(icon))
            .setContentText(body)
            .setContentTitle(title)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(Notification.DEFAULT_ALL)


        val noti = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        var i = 0
        if (j > 0) {
            i = j
        }
        noti.notify(i, notification2.build())
    }
}