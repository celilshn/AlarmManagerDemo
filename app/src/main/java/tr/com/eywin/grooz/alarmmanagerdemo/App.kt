package tr.com.eywin.grooz.alarmmanagerdemo

import android.app.Application
import android.app.Notification
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build

class App : Application() {
    private  val TAG = "Application"

    companion object {
        const val ACTION_START_SERVICE = "ACTION_START_SERVICE"
        const val EXTRA_NOTIFICATION_REQUEST_CODE = "EXTRA_NOTIFICATION_REQUEST_CODE"
        const val EXTRA_NOTIFICATION = "EXTRA_NOTIFICATION"
        const val NOTIFICATION_REQUEST_CODE = 100
    }

    private lateinit var serviceNotificationManager: ServiceNotificationManager
    private lateinit var notification: Notification

    override fun onCreate() {
        super.onCreate()
        println("APP : $TAG onCreate ")
        serviceNotificationManager = ServiceNotificationManager(applicationContext)
        notification = serviceNotificationManager.createNotification()
        serviceNotificationManager.notificationOnCreate(applicationContext)
        showNotification(applicationContext, notification)
    }

    private fun showNotification(
        context: Context, notification: Notification
    ) {
        val intent = Intent(context, AppLockService::class.java)
        intent.action = ACTION_START_SERVICE
        intent.putExtra(EXTRA_NOTIFICATION_REQUEST_CODE, NOTIFICATION_REQUEST_CODE)
        intent.putExtra(EXTRA_NOTIFICATION, notification)
        startService(applicationContext,intent)
    }
    private fun startService(context: Context, intent: Intent): Boolean {
        var componentName:ComponentName?=null
        if (Build.VERSION.SDK_INT >= 26) {
            /**Context.startForegroundService() did not then call Service.startForeground(): ServiceRecord{cf3feb4 u0 com.ibragunduz.applockpro/.service.AppLockService}
             * hatsı için eklendi CoroutineScope*/
                componentName = context.startForegroundService(intent)
        } else {
            componentName = context.startService(intent)
        }

        return componentName != null
    }

    override fun onTerminate() {
        super.onTerminate()
        println("APP : $TAG onTerminate ")

    }
}