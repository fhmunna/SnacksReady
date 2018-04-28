package com.w3engineers.core;

import android.content.Context;
import android.support.multidex.BuildConfig;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import com.squareup.leakcanary.LeakCanary;
import com.w3engineers.core.snacksready.R;
import com.w3engineers.core.snacksready.data.local.dbstorage.DatabaseService;
import com.w3engineers.core.util.helper.Glider;
import com.w3engineers.core.util.helper.Notify;
import com.w3engineers.core.util.helper.SharedPref;
import com.w3engineers.core.util.helper.Toaster;
import com.w3engineers.core.util.lib.network.NetworkService;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/*
* ****************************************************************************
* * Copyright © 2018 W3 Engineers Ltd., All rights reserved.
* *
* * Created by:
* * Name : Anjan Debnath
* * Date : 10/25/17
* * Email : anjan@w3engineers.com
* *
* * Purpose: Base Application class
* *
* * Last Edited by : SUDIPTA KUMAR PAIK on 03/08/18.
* * History:
* * 1:
* * 2:
* *
* * Last Reviewed by : SUDIPTA KUMAR PAIK on 03/08/18.
* ****************************************************************************
*/

public class AppController extends MultiDexApplication {
    private static Context sContext;

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();

        releaseLoader(sContext);
        debugLoader(sContext);
    }

    private void debugLoader(Context context) {
        if (!BuildConfig.DEBUG) return;

        //Timber.plant(new Timber.DebugTree());//skpaik: Comment Out
        //LeakCanary();

        //DemoHelper.startUpEvents();
    }

    private void releaseLoader(Context context) {
        NetworkService.start();
        Glider.init(context);
        Toaster.init(context);
        SharedPref.init(context);
        Notify.init(context);
        DatabaseService.init(context);

        calligraphy();
    }

    private void calligraphy() {
        CalligraphyConfig.initDefault(
                new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }

    private void LeakCanary() {
        if (!LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
        MultiDex.install(this);
    }
}