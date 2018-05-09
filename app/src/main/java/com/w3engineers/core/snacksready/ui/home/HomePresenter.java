package com.w3engineers.core.snacksready.ui.home;

import com.w3engineers.core.snacksready.data.local.prefstorage.PreferencesHelper;
import com.w3engineers.core.snacksready.data.local.sharedpreference.SharedPrefLoginInfo;
import com.w3engineers.core.snacksready.ui.base.BasePresenter;
import com.w3engineers.core.util.helper.TimeUtil;
import com.w3engineers.core.util.lib.alarm.AlarmHelper;

import java.util.Calendar;

public class HomePresenter extends BasePresenter<HomeMvpView>{
    SharedPrefLoginInfo loginInfo;

    HomePresenter(){
        loginInfo = PreferencesHelper.provideLoginInfoSharePrefService();
    }

    void loadLocalData(){
        String timeLeftSnacks = "";
        if(!loginInfo.isSnacksOrderedToday()){
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 12);
            calendar.set(Calendar.MINUTE, 0);
            long differ = TimeUtil.differ2(calendar.getTimeInMillis());
            if(differ > 0) {
                differ = differ/1000;
                if(differ>0 && differ<60) timeLeftSnacks = differ + " seconds left!";
                else if(differ>59 && differ<3600) timeLeftSnacks = differ/60 + " min left!";
                else timeLeftSnacks = differ/3600 + " hour left!";
            }
            else timeLeftSnacks = "Time's up!";
        }
        else timeLeftSnacks = "Confirmed!";

        String badgeLunch = "Confirm now!";
        if(loginInfo.isLunchConfirmed()) badgeLunch = "Confirmed";

        getMvpView().onLoadLocalData(loginInfo.isSnacksOrderedToday(), loginInfo.isLunchConfirmed(),
                loginInfo.isAlarmSet(), timeLeftSnacks, badgeLunch);
    }

    void setSnacksRemainder() {
        loginInfo.updateAlarmSet(true);
        AlarmHelper.triggerAlarmManager(10);
    }

    public void stopSnacksRemainder() {
        loginInfo.updateAlarmSet(false);
        AlarmHelper.stopAlarmManager();
    }
}
