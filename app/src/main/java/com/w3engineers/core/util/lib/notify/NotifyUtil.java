package com.w3engineers.core.util.lib.notify;

import android.app.PendingIntent;
import android.content.Context;

import br.com.goncalves.pugnotification.notification.PugNotification;

/*
* ****************************************************************************
* * Copyright © 2018 W3 Engineers Ltd., All rights reserved.
* *
* * Created by:
* * Name : SUDIPTA KUMAR PAIK
* * Date : 2/12/18
* * Email : sudipta@w3engineers.com
* *
* * Purpose: Main class to interact with library. You can add/edit method if required for your application
* *
* * Last Edited by : SUDIPTA KUMAR PAIK on 2/12/18.
* * History:
* * 1:
* * 2:
* * Last Reviewed by : SUDIPTA KUMAR PAIK on 03/08/18.
* ****************************************************************************
*/

public class NotifyUtil {
    /**
     * show simple notification
     *
     * @param context
     * @param pugModel
     */
    public void showSimpleNotification(Context context, NotifyModel pugModel) {
        PugNotification.with(context)
                .load()
                .title(pugModel.getTitle())
                .message(pugModel.getMessage())
                .identifier(pugModel.getNotificationId())
                .smallIcon(pugModel.getDrawableIcon())
                .largeIcon(pugModel.getDrawableIcon())
                .simple()
                .build();
    }

    /**
     * show notification with vibration
     *
     * @param context
     * @param pugModel
     */
    public void showVibrateNotification(Context context, NotifyModel pugModel) {
        PugNotification.with(context)
                .load()
                .title(pugModel.getTitle())
                .message(pugModel.getMessage())
                .identifier(pugModel.getNotificationId())
                .smallIcon(pugModel.getDrawableIcon())
                .largeIcon(pugModel.getDrawableIcon())
                .vibrate(new long[]{1000, 1000}) //  delay, vibrate, sleep, vibrate, sleep
                .simple()
                .build();
    }

    /**
     * show progress notification
     *
     * @param context
     * @param pugModel
     */
    public void showVibrateProgressNotification(Context context, NotifyModel pugModel) {
        PugNotification.with(context)
                .load()
                .title(pugModel.getTitle())
                .message(pugModel.getMessage())
                .identifier(pugModel.getNotificationId())
                .smallIcon(pugModel.getDrawableIcon())
                .largeIcon(pugModel.getDrawableIcon())
                .vibrate(new long[]{1000, 1000})  //  delay, vibrate, sleep, vibrate, sleep
                .progress()
                .value(0, 100, true)
                .build();
    }

    /**
     * show progress notification
     *
     * @param context
     * @param pugModel
     */
    public void showSimpleProgressNotification(Context context, NotifyModel pugModel) {
        PugNotification.with(context)
                .load()
                .title(pugModel.getTitle())
                .message(pugModel.getMessage())
                .identifier(pugModel.getNotificationId())
                .smallIcon(pugModel.getDrawableIcon())
                .largeIcon(pugModel.getDrawableIcon())
                .progress()
                .value(0, 100, true)
                .build();
    }

    /**
     * notification with click event
     *
     * @param context
     * @param pugModel
     * @param clickIntent
     */
    public void showClickToDismissNotification(Context context, NotifyModel pugModel, PendingIntent clickIntent) {
        PugNotification.with(context)
                .load()
                .title(pugModel.getTitle())
                .message(pugModel.getMessage())
                .autoCancel(true)
                .identifier(pugModel.getNotificationId())
                .smallIcon(pugModel.getDrawableIcon())
                .largeIcon(pugModel.getDrawableIcon())
                .vibrate(new long[]{1000, 1000})  //  delay, vibrate, sleep, vibrate, sleep
                .click(clickIntent)
                .simple()
                .build();
    }
}