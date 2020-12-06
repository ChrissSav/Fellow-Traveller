package gr.fellow.fellow_traveller.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.FellowApp.Companion.CHANNEL_TRIPS_ID
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.domain.NotificationStatus
import gr.fellow.fellow_traveller.domain.TripStatus
import gr.fellow.fellow_traveller.ui.extensions.getTextHtml
import gr.fellow.fellow_traveller.ui.home.HomeActivity
import gr.fellow.fellow_traveller.ui.rate.RateActivity
import gr.fellow.fellow_traveller.usecase.notification.GetNotificationsSocketUseCase
import gr.fellow.fellow_traveller.utils.NOTIFICATION_TIME_LOOP
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@AndroidEntryPoint
class NotificationService : Service() {


    @Inject
    lateinit var getNotificationsUseCase: GetNotificationsSocketUseCase

    @Inject
    lateinit var viewModelSecond: NotificationSocketViewModel
    private val mBinder: IBinder = MyBinder()
    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable

    private lateinit var notificationManager: NotificationManagerCompat

    override fun onCreate() {
        super.onCreate()
        mHandler = Handler()
        notificationManager = NotificationManagerCompat.from(this)

    }

    override fun onBind(intent: Intent): IBinder? {
        return mBinder
    }

    inner class MyBinder : Binder() {
        val service: NotificationService
            get() = this@NotificationService
    }


    fun startPretendLongRunningTask() {
        mRunnable = object : Runnable {
            override fun run() {
                runBlocking {
                    try {
                        val response = getNotificationsUseCase()
                        if (response.size > 0)
                            viewModelSecond.updateNotificationCount(response.size)
                        response.forEach {
                            sendOnChannel1(it)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                mHandler.postDelayed(this, NOTIFICATION_TIME_LOOP)
            }
        }
        mHandler.postDelayed(mRunnable, 250)
    }


    override fun onTaskRemoved(rootIntent: Intent) {
        super.onTaskRemoved(rootIntent)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacks(mRunnable)
    }

    private fun sendOnChannel1(notificationItem: gr.fellow.fellow_traveller.domain.notification.Notification) {
        val alarmSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)


        val notification: android.app.Notification = NotificationCompat.Builder(this, CHANNEL_TRIPS_ID)
            .setSmallIcon(R.drawable.ic_logo_white)
            .setContentTitle(getTitle(notificationItem))
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(getDescription(notificationItem))
            )
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(alarmSound)
            .build()

        if (notificationItem.type == NotificationStatus.RATE.code) {
            val intent = Intent(this, RateActivity::class.java)
            intent.putExtras(bundleOf("notification" to notificationItem))
            val pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            notification.contentIntent = pendingIntent

        } else if (notificationItem.type == NotificationStatus.PASSENGER_ENTER.code || notificationItem.type == NotificationStatus.PASSENGER_EXIT.code) {
            val p = NavDeepLinkBuilder(this)
                .setGraph(R.navigation.home_nav_graph)
                .setComponentName(HomeActivity::class.java)
                .setDestination(R.id.tripInvolvedDetailsSecondFragment)
                .setArguments(bundleOf("reload" to true, "history" to (notificationItem.trip.status == TripStatus.COMPLETED.code), "tripId" to notificationItem.trip.id, "creator" to true))
                .createPendingIntent()
            notification.contentIntent = p
        }

        notificationManager.notify(notificationItem.id.toInt(), notification)
    }


    private fun getDescription(item: gr.fellow.fellow_traveller.domain.notification.Notification): String {
        return when (item.type) {
            NotificationStatus.RATE.code -> {
                getString(R.string.notification_to_rate, item.user.fullName).getTextHtml()
            }
            NotificationStatus.PASSENGER_EXIT.code -> {
                getString(R.string.notification_passenger_exit, item.user.fullName).getTextHtml()
            }
            NotificationStatus.PASSENGER_ENTER.code -> {
                getString(R.string.notification_passenger_enter, item.user.fullName).getTextHtml()
            }
            else -> {
                getString(R.string.notification_delete_trip, item.user.fullName).getTextHtml()
            }
        }
    }

    private fun getTitle(item: gr.fellow.fellow_traveller.domain.notification.Notification): String {
        return when (item.type) {
            NotificationStatus.RATE.code -> {
                getString(R.string.notification_to_rate_title)
            }
            NotificationStatus.PASSENGER_EXIT.code -> {
                getString(R.string.notification_passenger_exit_title)
            }
            NotificationStatus.PASSENGER_ENTER.code -> {
                getString(R.string.notification_passenger_enter_title)
            }
            else -> {
                getString(R.string.notification_delete_trip_title)
            }
        }
    }
}
