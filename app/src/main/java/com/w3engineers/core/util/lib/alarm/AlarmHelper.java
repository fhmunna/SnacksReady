package com.w3engineers.core.util.lib.alarm;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.w3engineers.core.AppController;
import com.w3engineers.core.snacksready.data.local.appconst.AppConst;
import com.w3engineers.core.util.helper.Logger;

import java.util.Calendar;

public class AlarmHelper {
    private static PendingIntent pendingIntent;

    public AlarmHelper(){
        if(pendingIntent == null){
            /* Retrieve a PendingIntent that will perform a broadcast */
            Intent alarmIntent = new Intent(AppController.getContext(), AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(AppController.getContext(),
                    AppConst.ALARM_REQUEST_CODE, alarmIntent, AppConst.FLAG_ALARM);
        }
    }

    //Trigger alarm manager with entered time interval
    public void triggerAlarmManager(int alarmTriggerTime) {
        // get a Calendar object with current time
        Calendar cal = Calendar.getInstance();
        // add alarmTriggerTime seconds to the calendar object
        cal.add(Calendar.SECOND, alarmTriggerTime);

        //get instance of alarm manager
        AlarmManager manager = (AlarmManager) AppController.getContext().getSystemService(Context.ALARM_SERVICE);
        //set alarm manager with entered timer by converting into milliseconds
        manager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        //set a repeating alarm
        //manager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 10*1000, pendingIntent);

        Log.d("AlarmManager", "Alarm Set for " + alarmTriggerTime + " seconds.");
    }

    //Stop/Cancel alarm manager
    public void stopAlarmManager() {

        AlarmManager manager = (AlarmManager) AppController.getContext().getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);//cancel the alarm manager of the pending intent


        //Stop the Media Player Service to stop sound
        AppController.getContext().stopService(new Intent(AppController.getContext(), AlarmSoundService.class));

        //remove the notification from notification tray
        NotificationManager notificationManager = (NotificationManager)
                AppController.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(AlarmNotificationService.NOTIFICATION_ID);

        Log.d("AlarmManager", "Alarm Canceled/Stop.");
    }
}
