package tr.com.eywin.grooz.alarmmanagerdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.widget.Toast;

public class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Toast.makeText(p0, "Alarm Çalıyor! Artık Uyan!", Toast.LENGTH_LONG).show()
    }
}