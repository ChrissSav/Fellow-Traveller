package gr.fellow.fellow_traveller.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.FellowApp.Companion.CHANNEL_1_ID
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.ui.extensions.getTextHtml
import gr.fellow.fellow_traveller.usecase.notification.GetNotificationsSocketUseCase
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@AndroidEntryPoint
class NotificationService : Service() {


    companion object {
        private const val TAG = "MyService"
        private const val TIME = 10000L

    }

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
                        viewModelSecond.updateNotificationCount(response.size)
                        response.forEach {
                            sendOnChannel1(it)
                        }
                        Log.i(TAG, "rpojgirughregreg")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                mHandler.postDelayed(this, TIME)
            }
        }
        mHandler.postDelayed(mRunnable, 250)
    }


    override fun onTaskRemoved(rootIntent: Intent) {
        super.onTaskRemoved(rootIntent)
        Log.d(TAG, "onTaskRemoved: called.")
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacks(mRunnable)
        Log.d(TAG, "onDestroy: called.")
    }

    private fun sendOnChannel1(notificationItem: gr.fellow.fellow_traveller.domain.notification.Notification) {

        val notification: android.app.Notification = NotificationCompat.Builder(this, CHANNEL_1_ID)
            .setSmallIcon(R.drawable.ic_logo)
            .setContentTitle(getTitle(notificationItem))
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(getDescription(notificationItem))
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .build()

        notificationManager.notify(notificationItem.id.toInt(), notification)
    }


    private fun getDescription(item: gr.fellow.fellow_traveller.domain.notification.Notification): String {
        return when (item.type) {
            0 -> {
                getString(R.string.notification_to_rate, item.user.fullName).getTextHtml()
            }
            1 -> {
                getString(R.string.notification_passenger_exit, item.user.fullName).getTextHtml()
            }
            2 -> {
                getString(R.string.notification_passenger_enter, item.user.fullName).getTextHtml()
            }
            else -> {
                getString(R.string.notification_delete_trip, item.user.fullName).getTextHtml()
            }
        }
    }

    private fun getTitle(item: gr.fellow.fellow_traveller.domain.notification.Notification): String {
        return when (item.type) {
            0 -> {
                getString(R.string.notification_to_rate_title)
            }
            1 -> {
                getString(R.string.notification_passenger_exit_title)
            }
            2 -> {
                getString(R.string.notification_passenger_enter_title)
            }
            else -> {
                getString(R.string.notification_delete_trip_title)
            }
        }
    }
}
