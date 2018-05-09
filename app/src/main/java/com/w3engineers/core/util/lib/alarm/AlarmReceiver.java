package com.w3engineers.core.util.lib.alarm;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.w3engineers.core.snacksready.data.local.prefstorage.PreferencesHelper;
import com.w3engineers.core.snacksready.data.local.sharedpreference.SharedPrefLoginInfo;

import static android.support.v4.content.WakefulBroadcastReceiver.startWakefulService;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPrefLoginInfo loginInfo = PreferencesHelper.provideLoginInfoSharePrefService();

        if(!loginInfo.isSnacksOrderedToday()) {
            Toast.makeText(context, "Snacks Time!! Order Now!!", Toast.LENGTH_SHORT).show();

            //Stop sound service to play sound for alarm
            context.startService(new Intent(context, AlarmSoundService.class));

            //This will send a notification message and show notification in notification tray
            ComponentName comp = new ComponentName(context.getPackageName(),
                    AlarmNotificationService.class.getName());
            startWakefulService(context, (intent.setComponent(comp)));
        }
    }
}
