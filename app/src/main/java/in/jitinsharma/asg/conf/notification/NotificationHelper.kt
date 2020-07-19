package `in`.jitinsharma.asg.conf.notification

import `in`.jitinsharma.asg.conf.R
import `in`.jitinsharma.asg.conf.model.ConferenceData
import `in`.jitinsharma.asg.conf.ui.MainActivity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

object NotificationHelper {

    private const val CONF_UPDATE_CHANNEL = "CONFERENCE_UPDATE"

    fun sendConferenceUpdateNotification(context: Context, conferenceList: List<ConferenceData>) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(context, System.currentTimeMillis().toInt(), intent, 0)
        createNotificationChannel(context)

        val builder = NotificationCompat.Builder(context, CONF_UPDATE_CHANNEL)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Conferences Updated/Added")
            .setStyle(
                NotificationCompat.BigTextStyle().bigText(getConferenceListText(conferenceList))
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        NotificationManagerCompat.from(context).notify(1, builder.build())
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Conference Updates"
            val descriptionText = "Notifications for updates to conferences"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CONF_UPDATE_CHANNEL, name, importance).apply {
                description = descriptionText
            }
            NotificationManagerCompat.from(context).createNotificationChannel(channel)
        }
    }

    private fun getConferenceListText(conferenceList: List<ConferenceData>): String {
        return buildString {
            conferenceList.forEach {
                append("- ${it.name}, ${it.country}")
                appendln()
            }
        }
    }
}