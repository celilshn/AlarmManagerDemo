package tr.com.eywin.grooz.alarmmanagerdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyEventReceiver : BroadcastReceiver() {
    private val TAG = "MyEventReceiver"
    override fun onReceive(p0: Context?, intent: Intent?) {
        println("APP : $TAG onReceive ")

        if (Intent.ACTION_PACKAGE_REPLACED.equals(intent?.action)) {
            println("APP : $TAG Intent.ACTION_PACKAGE_REPLACED.equals(intent?.action) ")
            if (intent?.data?.schemeSpecificPart.equals(p0?.packageName)) {
                println("APP : $TAG intent?.data?.schemeSpecificPart.equals(p0?.packageName) ")
            }
        }
    }

}
