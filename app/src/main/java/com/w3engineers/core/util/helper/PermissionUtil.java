package com.w3engineers.core.util.helper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

/*
* ****************************************************************************
* * Copyright © 2018 W3 Engineers Ltd., All rights reserved.
* *
* * Created by:
* * Name : Aziz
* * Date : 16/10/17
* * Email : aziz@w3engineers.com
* *
* * Purpose : App permission util. Please don't change without discussion with CTO
* *
* * Last Edited by : SUDIPTA KUMAR PAIK on 08/03/18.
* * History:
* * 1:
* * 2:
* *
* * Last Reviewed by : SUDIPTA KUMAR PAIK on 08/03/18.
* ****************************************************************************
*/

public class PermissionUtil {
    public static final int REQUEST_CODE_PERMISSION_DEFAULT = 1;
    public static final int REQUEST_CODE_CONTACTS = 2;
    public static final int REQUEST_CODE_PERMISSION_CAMERA = 3;
    public static final int REQUEST_CODE_PERMISSION_FOOTER_GALLERY = 4;

    private static Context mContext;
    private static PermissionUtil invokePermission;

    private PermissionUtil() {

    }

    public static synchronized PermissionUtil on(Context context) {
        if (invokePermission == null) {
            invokePermission = new PermissionUtil();
        }

        mContext = context;

        return invokePermission;
    }

    public boolean request(String... str) {

        if (mContext == null) return false;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        List<String> finalArgs = new ArrayList<>();
        for (int i = 0; i < str.length; i++) {
            if (mContext.checkSelfPermission(str[i]) != PackageManager.PERMISSION_GRANTED) {
                finalArgs.add(str[i]);
            }
        }

        if (finalArgs.isEmpty()) {
            return true;
        }

        ((Activity) mContext).requestPermissions(finalArgs.toArray(new String[finalArgs.size()]), REQUEST_CODE_PERMISSION_DEFAULT);

        return false;
    }

    public boolean request(int requestCode, String... str) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        List<String> finalArgs = new ArrayList<>();
        for (int i = 0; i < str.length; i++) {
            if (mContext.checkSelfPermission(str[i]) != PackageManager.PERMISSION_GRANTED) {
                finalArgs.add(str[i]);
            }
        }

        if (finalArgs.isEmpty()) {
            return true;
        }

        ((Activity) mContext).requestPermissions(finalArgs.toArray(new String[finalArgs.size()]), requestCode);

        return false;
    }

    public boolean isAllowed(String str) {
        if (mContext == null) return false;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if (mContext.checkSelfPermission(str) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }

        return false;
    }
}
