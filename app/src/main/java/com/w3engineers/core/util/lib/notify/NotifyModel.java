package com.w3engineers.core.util.lib.notify;

/*
* ****************************************************************************
* * Copyright © 2018 W3 Engineers Ltd., All rights reserved.
* *
* * Created by:
* * Name : SUDIPTA KUMAR PAIK
* * Date : 2/12/18
* * Email : sudipta@w3engineers.com
* *
* * Purpose: Notification Model class to create notification. You can add/edit property if required for your application
* *
* * Last Edited by : SUDIPTA KUMAR PAIK on 2/12/18.
* * History:
* * 1:
* * 2:
* * Last Reviewed by : SUDIPTA KUMAR PAIK on 03/08/18.
* ****************************************************************************
*/


import com.w3engineers.core.snacksready.R;

public class NotifyModel {

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getDrawableIcon() {
        return drawableIcon;
    }

    public void setDrawableIcon(int drawableIcon) {
        this.drawableIcon = drawableIcon;
    }

    private String title;
    private String message;
    private int notificationId;
    private int drawableIcon = R.mipmap.ic_launcher;
}
