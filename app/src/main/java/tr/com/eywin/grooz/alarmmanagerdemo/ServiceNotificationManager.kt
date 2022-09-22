package tr.com.eywin.grooz.alarmmanagerdemo

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat.*

class ServiceNotificationManager(val context: Context) {
    private var notificationManager: NotificationManager
    val CHANNEL_ID_NOTI_SERVICE = "tr.com.eywin.grooz.alarmmanagerdemo"
    val CHANNEL_ID_NOTI = 19

    init {
        notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    fun createNotification(): Notification {
        createAppLockerServiceChannel()

        val resultIntent = Intent(context, MainActivity::class.java)
        val resultPendingIntent = PendingIntent.getActivity(
            context,
            0,
            resultIntent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }
        )

        val importance = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NotificationManager.IMPORTANCE_LOW
        } else {
            PRIORITY_LOW
        }
        return Builder(context, CHANNEL_ID_APPLOCKER_SERVICE)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(resultPendingIntent)
            .setSound(null)
            .setStyle(InboxStyle().setSummaryText("secured by Applock Pro"))
            .setDefaults(0)
            .setVisibility(VISIBILITY_SECRET)
            .setPriority(importance)
            .build()
    }


    private fun createAppLockerServiceChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Lock Status Notification"
            val descriptionText = "Service Running"
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel =
                NotificationChannel(CHANNEL_ID_APPLOCKER_SERVICE, name, importance).apply {
                    description = descriptionText
                }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val CHANNEL_ID_APPLOCKER_SERVICE = "CHANNEL_ID_APPLOCKER_SERVICE"
    }

    fun notificationOnCreate(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Hide Notification"
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel =
                NotificationChannel(CHANNEL_ID_NOTI_SERVICE, name, importance)
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val pattern = longArrayOf(0, 100, 1000, 300)
            channel.vibrationPattern = pattern
            channel.description = context.getString(R.string.app_name)
            channel.setShowBadge(false)
            channel.lockscreenVisibility = Notification.VISIBILITY_SECRET
            notificationManager.createNotificationChannel(channel)
        }
    }

}