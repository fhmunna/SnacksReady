package com.w3engineers.core.util.helper;

import android.util.Log;

/*
* ****************************************************************************
* * Copyright © 2018 W3 Engineers Ltd., All rights reserved.
* *
* * Created by:
* * Name : Azizul Islam
* * Date : 10/16/17
* * Email : azizul@w3engineers.com
* *
* * Purpose: App logging system API
* *
* * Last Edited by : SUDIPTA KUMAR PAIK on 03/13/18.
* * History:
* * 1:
* * 2:
* *
* * Last Reviewed by : SUDIPTA KUMAR PAIK on 03/15/18.
* ****************************************************************************
*/

public final class Logger {
    private static final String TAG = Logger.class.getName();

    private Logger() {
    }

    /*
    * General Log: Verbose Type
    * Logger will be default tag
    * */
    public static void log(String log) {
        v(null, log);
    }

    /*
    * General Log with Tag: Verbose Type
    * */
    public static void log(String tag, String log) {
        Log.v(tag == null ? TAG : tag, log);
    }

    /*
    * Error Log
    * Logger will be default tag
    * */
    public static void error(String log) {
        v(null, log);
    }

    /*
     * Error Log with Tag
     * */
    public static void error(String tag, String log) {
        Log.e(tag == null ? TAG : tag, log);
    }

    public static void v(String log) {
        v(null, log);
    }

    public static void e(String log) {
        e(null, log);
    }

    public static void v(String tag, String log) {
        Log.v(tag == null ? TAG : tag, log);
    }

    public static void e(String tag, String log) {
        Log.e(tag == null ? TAG : tag, log);
    }

    /*
    * WTF" stands for "What a Terrible Failure!" of course.
    * */
    public static void wtf(Throwable tr) {
        Log.wtf(TAG, tr);
    }
}