package com.w3engineers.core.util.helper;

import android.content.Context;
import android.widget.Toast;

/*
* ****************************************************************************
* * Copyright © 2018 W3 Engineers Ltd., All rights reserved.
* *
* * Created by:
* * Name : SUDIPTA KUMAR PAIK
* * Date : 2/1/18
* * Email : sudipta@w3engineers.com
* *
* * Purpose : Toaster for all type of toast showing
* *
* * Last Edited by : SUDIPTA KUMAR PAIK on 2/1/18.
* * History:
* * 1:
* * 2:
* *
* * Last Reviewed by : SUDIPTA KUMAR PAIK on 2/1/18.
* ****************************************************************************
*/

public class Toaster {
    private static Context sContext;

    /*
    * Private constructor. Don't make it public
    * */
    private Toaster() {
    }

    /*
    * Init Toast message context only one time
    * */
    public static void init(Context context) {
        sContext = context;
    }

    /*
    * Show long toast message
    * */
    public static void show(String txt) {
        Toast.makeText(sContext, txt, Toast.LENGTH_SHORT).show();
    }

    /*
    * Show short toast message
    * */
    public static void showShort(String txt) {
        Toast.makeText(sContext, txt, Toast.LENGTH_SHORT).show();
    }
}