package com.w3engineers.core.util.helper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.widget.RemoteViews;

import com.w3engineers.core.AppController;
import com.w3engineers.core.snacksready.R;

import br.com.goncalves.pugnotification.notification.PugNotification;

/*
* ****************************************************************************
* * Copyright © 2018 W3 Engineers Ltd., All rights reserved.
* *
* * Created by:
* * Name : SUDIPTA KUMAR PAIK
* * Date : 1/22/18
* * Email : sudipta@w3engineers.com
* *
* * Purpose :
* *
* * Last Edited by : SUDIPTA KUMAR PAIK on 1/22/18.
* * History:
* * 1:
* * 2:
* *  
* * Last Reviewed by : SUDIPTA KUMAR PAIK on 1/22/18.
* ****************************************************************************
*/

public class Notify {
    public static void show(Context context, String title, String subTitle) {
        if (context == null) return;

        RemoteViews expandedView = new RemoteViews(context.getPackageName(), R.layout.notification_message_collapsed_view);
        expandedView.setTextViewText(R.id.text_time, TimeUtil.getOnlyTime(TimeUtil.currentTime()));
        expandedView.setTextViewText(R.id.title, AppController.getContext().getResources().getString(R.string.app_name));
        expandedView.setTextViewText(R.id.text, "Message");
        Intent replayIntent = new Intent(NotifyUtil.REPLY_ACTION);
        //replayIntent.putExtra(User.class.getName(), user);


        expandedView.setOnClickPendingIntent(R.id.left_button, PendingIntent.getBroadcast(context, 1, replayIntent, PendingIntent.FLAG_CANCEL_CURRENT));

        Intent markSeenIntent = new Intent(NotifyUtil.MAKE_AS_READ);
        //markSeenIntent.putExtra("see", messageBase.messageId);

        expandedView.setOnClickPendingIntent(R.id.right_button, PendingIntent.getBroadcast(context, 1, markSeenIntent, PendingIntent.FLAG_CANCEL_CURRENT));

        RemoteViews collapsedView = new RemoteViews(context.getPackageName(), R.layout.notification_message_content);
        collapsedView.setTextViewText(R.id.text_time, TimeUtil.getOnlyTime(TimeUtil.currentTime()));
        collapsedView.setTextViewText(R.id.title, "Text Message");
        collapsedView.setTextViewText(R.id.text, "From User");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "M_CH_ID");

        builder.setSmallIcon(R.drawable.ic_app)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setAutoCancel(false)
                .setCustomContentView(collapsedView)
                .setCustomBigContentView(expandedView)
                .setStyle(new NotificationCompat.BigTextStyle()).setWhen(0);


        // retrieves android.app.NotificationManager
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, builder.build());
    }

    private static Context sContext;

    private Notify() {
    }

    public static void init(Context context) {
        sContext = context;
    }

    public static void show2(String title, String message, String bigText) {
        PugNotification.with(sContext)
                .load()
                .title(title)
                .message(message)
                .bigTextStyle(bigText)
                .smallIcon(R.drawable.pugnotification_ic_launcher)
                .largeIcon(R.drawable.pugnotification_ic_launcher)
                .flags(Notification.DEFAULT_ALL)
                .simple()
                .build();
    }
}