package gr.fellow.fellow_traveller.service

import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import dagger.hilt.android.AndroidEntryPoint
import gr.fellow.fellow_traveller.SynoditisApp
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.domain.NotificationStatus
import gr.fellow.fellow_traveller.domain.TripStatus
import gr.fellow.fellow_traveller.ui.extensions.getTextHtml
import gr.fellow.fellow_traveller.ui.home.HomeActivity
import gr.fellow.fellow_traveller.ui.rate.RateActivity
import gr.fellow.fellow_traveller.usecase.notification.GetNotificationsSocketUseCase
import gr.fellow.fellow_traveller.utils.NOTIFICATION_TIME_LOOP
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@AndroidEntryPoint
class NotificationJobService : JobService() {

    companion object {
        const val TAG = "NotificationJobService"
    }

    private var jobCancelled = false

    @Inject
    lateinit var getNotificationsUseCase: GetNotificationsSocketUseCase

    @Inject
    lateinit var viewModelSecond: NotificationSocketViewModel
    private lateinit var notificationManager: NotificationManagerCompat


    override fun onStartJob(params: JobParameters): Boolean {
        Log.d(TAG, "Job started")
        notificationManager = NotificationManagerCompat.from(this)
        doBackgroundWork(params)
        return true;
    }

    override fun onStopJob(p0: JobParameters): Boolean {
        Log.d(TAG, "Job cancelled before completion");
        jobCancelled = true;
        return true;
    }

    private fun doBackgroundWork(params: JobParameters) {
        Thread {
            for (i in 0..200) {
                Log.d(TAG, "Job finished $i")

                runBlocking {
                    try {
                        val response = getNotificationsUseCase()
                        if (response.size > 0)
                            viewModelSecond.updateNotificationCount(response.size)
                        response.forEach {
                            sendOnChannel1(it)
                        }
                        delay(NOTIFICATION_TIME_LOOP)
                    } catch (e: Exception) {

                    }
                }
            }
            Log.d(TAG, "Job finished")
            jobFinished(params, false)
        }.start()
    }


    private fun sendOnChannel1(notificationItem: gr.fellow.fellow_traveller.domain.notification.Notification) {
        val alarmSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)


        val notification: android.app.Notification = NotificationCompat.Builder(this, SynoditisApp.CHANNEL_TRIPS_ID)
            .setSmallIcon(R.drawable.ic_logo_white)
            .setContentTitle(getTitle(notificationItem))
            .setStyle(NotificationCompat.BigTextStyle().bigText(getDescription(notificationItem)))
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(alarmSound)
            .build()

        if (notificationItem.type == NotificationStatus.RATE) {
            val intent = Intent(this, RateActivity::class.java)
            intent.putExtras(bundleOf("notification" to notificationItem))
            val pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            notification.contentIntent = pendingIntent

        } else if (notificationItem.type == NotificationStatus.PASSENGER_ENTER || notificationItem.type == NotificationStatus.PASSENGER_EXIT) {
            val pendingIntent = NavDeepLinkBuilder(this)
                .setGraph(R.navigation.home_nav_graph)
                .setComponentName(HomeActivity::class.java)
                .setDestination(R.id.tripInvolvedDetailsSecondFragment)
                .setArguments(
                    bundleOf(
                        "notificationId" to notificationItem.id,
                        "reload" to true,
                        "history" to (notificationItem.trip.status == TripStatus.COMPLETED),
                        "tripId" to notificationItem.trip.id,
                        "creator" to true
                    )
                )
                .createPendingIntent()
            notification.contentIntent = pendingIntent
        }

        notificationManager.notify(notificationItem.id.toInt(), notification)
    }


    private fun getDescription(item: gr.fellow.fellow_traveller.domain.notification.Notification): String {
        return when (item.type) {
            NotificationStatus.RATE -> {
                getString(R.string.notification_to_rate, item.user.fullName).getTextHtml()
            }
            NotificationStatus.PASSENGER_EXIT -> {
                getString(R.string.notification_passenger_exit, item.user.fullName).getTextHtml()
            }
            NotificationStatus.PASSENGER_ENTER -> {
                getString(R.string.notification_passenger_enter, item.user.fullName).getTextHtml()
            }
            else -> {
                getString(R.string.notification_delete_trip, item.user.fullName).getTextHtml()
            }
        }
    }

    private fun getTitle(item: gr.fellow.fellow_traveller.domain.notification.Notification): String {
        return when (item.type) {
            NotificationStatus.RATE -> {
                getString(R.string.notification_to_rate_title)
            }
            NotificationStatus.PASSENGER_EXIT -> {
                getString(R.string.notification_passenger_exit_title)
            }
            NotificationStatus.PASSENGER_ENTER -> {
                getString(R.string.notification_passenger_enter_title)
            }
            else -> {
                getString(R.string.notification_delete_trip_title)
            }
        }
    }


}
