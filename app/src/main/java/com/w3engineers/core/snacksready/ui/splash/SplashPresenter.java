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


import com.w3engineers.core.snacksready.data.local.prefstorage.PreferencesHelper;
import com.w3engineers.core.snacksready.data.local.sharedpreference.SharedPrefLoginInfo;
import com.w3engineers.core.snacksready.data.local.user.User;
import com.w3engineers.core.snacksready.ui.base.BasePresenter;
import com.w3engineers.core.util.helper.Logger;
import com.w3engineers.core.util.lib.alarm.AlarmHelper;
import com.w3engineers.core.util.lib.network.NetworkService;

public class SplashPresenter extends BasePresenter<SplashMvpView> {
    private SharedPrefLoginInfo sharedPrefLoginInfo;

    SplashPresenter(){
        sharedPrefLoginInfo = PreferencesHelper.provideLoginInfoSharePrefService();

        //new AlarmHelper().triggerAlarmManager(5);
    }

    public void whereToGo(){
        if(sharedPrefLoginInfo.isRemembered()) {
            getMvpView().onRemembered(sharedPrefLoginInfo.getOfficeId());
        }
        else {
            if(sharedPrefLoginInfo.getOfficeId().isEmpty()) getMvpView().onNewSignIn();
            else getMvpView().onForgot();
        }
    }

    void checkValidity(String officeId){
        NetworkService.checkUserValidity(officeId);
    }

    void processNewUser(User user, int avatar){
        AlarmHelper.triggerAlarmManager(10);
        if(avatar==-1) avatar = sharedPrefLoginInfo.getAvatar();
        sharedPrefLoginInfo.storeLoginInfo(user.getOfficeId(), avatar, true, false, true);

        getMvpView().onValidSignIn();
    }

    void processOldUser(boolean isRemembered, boolean isOrderedToday){
        sharedPrefLoginInfo.updateRemembered(isRemembered);
        sharedPrefLoginInfo.updateOrderedToday(isOrderedToday);

        getMvpView().onValidSignIn();
    }
}