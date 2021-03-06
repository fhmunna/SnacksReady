/*
*  ****************************************************************************
*  * Created by : Azizul Islam on 13-Oct-17 at 4:02 PM.
*  * Email : azizul@w3engineers.com
*  *
*  * Last edited by : Azizul Islam on 13-Oct-17.
*  *
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
*  ****************************************************************************
*/
package com.w3engineers.core.snacksready.ui.splash;

import android.text.TextUtils;

import com.w3engineers.core.snacksready.data.local.prefstorage.PreferencesHelper;
import com.w3engineers.core.snacksready.data.local.sharedpreference.SharedPrefLoginInfo;
import com.w3engineers.core.snacksready.ui.base.BasePresenter;

public class SplashPresenter extends BasePresenter<SplashMvpView> {
    private SharedPrefLoginInfo sharedPrefLoginInfo;

    SplashPresenter(){
        sharedPrefLoginInfo = PreferencesHelper.provideLoginInfoSharePrefService();
    }

    public void whereToGo(){
        if(sharedPrefLoginInfo.isRemembered()) {
            checkSignInValidity(sharedPrefLoginInfo.getOfficeId(), true);
        }
        else {
            if(sharedPrefLoginInfo.getOfficeId().isEmpty()) getMvpView().onNewSignIn();
            else getMvpView().onForgot();
        }
    }

    void checkSignInValidity(String officeId, boolean isRemembered){
        if(TextUtils.isEmpty(officeId) || !sharedPrefLoginInfo.getOfficeId().equals(officeId))
            getMvpView().onInvalidSignIn();
        else {
            sharedPrefLoginInfo.updateRemembered(isRemembered);
            getMvpView().onValidSignIn();
        }
    }

    public void saveUserInfo(int avatar, String officeId){
        sharedPrefLoginInfo.storeLoginInfo(officeId, avatar, true);
        getMvpView().onValidSignIn();
    }
}