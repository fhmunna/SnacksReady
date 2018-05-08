package com.w3engineers.core.snacksready.ui.admin;
/*
 *  ****************************************************************************
 *  * Created by : Md. Hasnain on 5/8/2018 at 11:17 AM.
 *  * Email : ashik.pstu.cse@gmail.com
 *  *
 *  * Last edited by : Md. Hasnain on 5/8/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

import com.w3engineers.core.snacksready.data.local.prefstorage.PreferencesHelper;
import com.w3engineers.core.snacksready.data.local.sharedpreference.SharedPrefLoginInfo;
import com.w3engineers.core.snacksready.ui.base.BasePresenter;
import com.w3engineers.core.util.lib.network.NetworkService;

public class AdminPresenter extends BasePresenter<AdminMvpView>{
    private SharedPrefLoginInfo sharedPrefLoginInfo;

    AdminPresenter(){
        sharedPrefLoginInfo = PreferencesHelper.provideLoginInfoSharePrefService();
    }

    void checkAdminValidity(String pin){
        NetworkService.checkAdminValidity(sharedPrefLoginInfo.getOfficeId(), pin);
    }
}
