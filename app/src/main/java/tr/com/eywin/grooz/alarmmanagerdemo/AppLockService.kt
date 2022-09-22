package tr.com.eywin.grooz.alarmmanagerdemo

import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.Parcelable
import tr.com.eywin.grooz.alarmmanagerdemo.App.Companion.ACTION_START_SERVICE
import tr.com.eywin.grooz.alarmmanagerdemo.App.Companion.EXTRA_NOTIFICATION
import tr.com.eywin.grooz.alarmmanagerdemo.App.Companion.EXTRA_NOTIFICATION_REQUEST_CODE


class AppLockService : Service() {
    private val TAG = "AppLockService"
    override fun onCreate() {
        super.onCreate()
        println("APP : $TAG onCreate ")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        println("APP : $TAG onStartCommand ")
        if (intent != null) {
            when (intent.action) {
                ACTION_START_SERVICE -> {
                    val extras = intent.extras
                    if (extras != null) {
                        if (extras.containsKey(EXTRA_NOTIFICATION)) {
                            val notification = extras.getParcelable<Parcelable>(EXTRA_NOTIFICATION)
                            if (notification is Notification) {
                                if (extras.containsKey(EXTRA_NOTIFICATION_REQUEST_CODE)) {
                                    val requestCode = extras.getInt(EXTRA_NOTIFICATION_REQUEST_CODE)
                                    startForeground(requestCode, notification)
                                }
                            }
                        }
                    }
                }
            }
        }
        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        println("APP : $TAG onTaskRemoved ")
        val intent = Intent(
            applicationContext, AppLockService::class.java
        )
        val pendingIntent = PendingIntent.getService(
            this, 1, intent, if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT
            } else {
                PendingIntent.FLAG_ONE_SHOT
            }
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        println("APP : $TAG onDestroy ")

    }

}