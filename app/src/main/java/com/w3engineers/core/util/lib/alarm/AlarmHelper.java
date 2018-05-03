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
    private static PendingIntent pendingIntent1, pendingIntent2, pendingIntent3;

    public static void init(){
        if(pendingIntent1 == null){
            /* Retrieve a PendingIntent that will perform a broadcast */
            Intent alarmIntent = new Intent(AppController.getContext(), AlarmReceiver.class);
            pendingIntent1 = PendingIntent.getBroadcast(AppController.getContext(),
                    AppConst.ALARM1_REQUEST_CODE, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        if(pendingIntent2 == null){
            /* Retrieve a PendingIntent that will perform a broadcast */
            Intent alarmIntent = new Intent(AppController.getContext(), AlarmReceiver.class);
            pendingIntent2 = PendingIntent.getBroadcast(AppController.getContext(),
                    AppConst.ALARM2_REQUEST_CODE, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        if(pendingIntent3 == null){
            /* Retrieve a PendingIntent that will perform a broadcast */
            Intent alarmIntent = new Intent(AppController.getContext(), AlarmReceiver.class);
            pendingIntent3 = PendingIntent.getBroadcast(AppController.getContext(),
                    AppConst.ALARM3_REQUEST_CODE, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
    }

    //Trigger alarm manager with entered time interval
    public static void triggerAlarmManager(int hourOfDay) {
        // get a Calendar object with current time
        Calendar calendar = Calendar.getInstance();
        // add alarmTriggerTime seconds to the calendar object
        //calendar.add(Calendar.SECOND, alarmTriggerTime);
        calendar.set(calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(calendar.MINUTE, 15);
        calendar.set(calendar.SECOND, 0);
        calendar.set(calendar.MILLISECOND, 0);

        //get instance of alarm manager
        AlarmManager manager = (AlarmManager) AppController.getContext().getSystemService(Context.ALARM_SERVICE);
        //set alarm manager with entered timer by converting into milliseconds
        //manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        //set a repeating alarm
        manager.setRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent1);

        calendar.set(calendar.HOUR_OF_DAY, hourOfDay+1);
        calendar.set(calendar.MINUTE, 0);
        manager.setRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent2);

        calendar.set(calendar.MINUTE, 45);
        manager.setRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent3);

        Log.d("AlarmManager", "Alarm Set for 1 day's interval.");
    }

    //Stop/Cancel alarm manager
    public static void stopAlarmManager() {
        AlarmManager manager = (AlarmManager) AppController.getContext().getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent1);//cancel the alarm manager of the pending intent
        manager.cancel(pendingIntent2);
        manager.cancel(pendingIntent3);


        //Stop the Media Player Service to stop sound
        AppController.getContext().stopService(new Intent(AppController.getContext(), AlarmSoundService.class));

        //remove the notification from notification tray
        NotificationManager notificationManager = (NotificationManager)
                AppController.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(AlarmNotificationService.NOTIFICATION_ID);

        Log.d("AlarmManager", "Alarm Canceled/Stop.");
    }
}
