package com.w3engineers.core.snacksready.data.local.prefstorage;

import com.w3engineers.core.AppController;
import com.w3engineers.core.snacksready.data.local.sharedpreference.SharedPrefLoginInfo;

/*
 *  ****************************************************************************
 *  * Created by : Md. Hasnain on 4/13/2018 at 10:58 AM.
 *  * Email : ashik.pstu.cse@gmail.com
 *  *
 *  * Last edited by : Md. Hasnain on 4/13/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */public class PreferencesHelper {
    public static SharedPrefLoginInfo provideLoginInfoSharePrefService() {
        return new SharedPrefLoginInfo(AppController.getContext());
    }
}
