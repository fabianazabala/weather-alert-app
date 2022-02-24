package com.climate_dissertation_app.ui.notification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.climate_dissertation_app.service.CurrentRecommendation
import com.climate_dissertation_app.service.RecommendationService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


const val RECOMMENDATION_NOTIFICATION_CHANNEL = "weather-notification-channel"

@AndroidEntryPoint
class RegularNotificationService @Inject constructor() : JobService() {

    @Inject
    lateinit var recommendationService: RecommendationService

    @SuppressLint("MissingPermission")
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        recommendationService.refreshRecommendation(applicationContext) {
            sendNotification(it)
        }

        scheduleJob(applicationContext)
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        return true
    }


    companion object {
        fun scheduleJob(context: Context) {
            val serviceComponent = ComponentName(context, RegularNotificationService::class.java)
            val builder = JobInfo.Builder(0, serviceComponent)
            builder.setMinimumLatency(1000 * 60 * 5)

            builder.setOverrideDeadline(1000 * 60 * 10)

            val jobScheduler = context.getSystemService(JobScheduler::class.java)
            jobScheduler.schedule(builder.build())
        }
    }

    private fun sendNotification(recommendation: CurrentRecommendation) {

        val notification = NotificationCompat.Builder(this, RECOMMENDATION_NOTIFICATION_CHANNEL)
            .setSmallIcon(recommendation.weatherIconResourceId)
            .setContentTitle("Outfit recommendation!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(recommendation.notificationText)
            )
            .setLocalOnly(true)
            .setAutoCancel(true)
            .build()

        with(NotificationManagerCompat.from(this)) {
            notify(1, notification)
        }
    }

    private fun createNotificationChannel() {
        val name = RECOMMENDATION_NOTIFICATION_CHANNEL
        val descriptionText = "channel to send regular recommendations based on weather"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel =
            NotificationChannel(RECOMMENDATION_NOTIFICATION_CHANNEL, name, importance).apply {
                description = descriptionText
            }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}