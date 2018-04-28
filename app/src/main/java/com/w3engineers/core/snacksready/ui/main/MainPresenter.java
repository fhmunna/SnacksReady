package com.w3engineers.core.snacksready.ui.main;
/*
 *  ****************************************************************************
 *  * Created by : Md. Hasnain on 4/24/2018 at 12:15 PM.
 *  * Email : ashik.pstu.cse@gmail.com
 *  *
 *  * Last edited by : Md. Hasnain on 4/24/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

import com.w3engineers.core.snacksready.data.local.prefstorage.PreferencesHelper;
import com.w3engineers.core.snacksready.data.local.sharedpreference.SharedPrefLoginInfo;
import com.w3engineers.core.snacksready.ui.base.BasePresenter;

public class MainPresenter extends BasePresenter<MainMvpView> {
    private SharedPrefLoginInfo loginInfo;

    MainPresenter(){
        loginInfo = PreferencesHelper.provideLoginInfoSharePrefService();
    }
}
