package com.w3engineers.core.snacksready.ui.profile;
/*
 *  ****************************************************************************
 *  * Created by : Md. Hasnain on 4/24/2018 at 6:35 PM.
 *  * Email : ashik.pstu.cse@gmail.com
 *  *
 *  * Last edited by : Md. Hasnain on 4/24/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

import com.w3engineers.core.snacksready.R;
import com.w3engineers.core.snacksready.data.local.appconst.AppConst;
import com.w3engineers.core.snacksready.data.local.prefstorage.PreferencesHelper;
import com.w3engineers.core.snacksready.data.local.sharedpreference.SharedPrefLoginInfo;
import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteUser;
import com.w3engineers.core.snacksready.ui.base.BasePresenter;
import com.w3engineers.core.util.helper.NetworkUtil;
import com.w3engineers.core.util.helper.TimeUtil;
import com.w3engineers.core.util.lib.network.NetworkService;

import java.util.Calendar;

public class ProfilePresenter extends BasePresenter<ProfileMvpView> {
    private SharedPrefLoginInfo sharedPrefLoginInfo;

    ProfilePresenter(){
        sharedPrefLoginInfo = PreferencesHelper.provideLoginInfoSharePrefService();
    }

    void loadData(){
        NetworkService.checkUserValidity(sharedPrefLoginInfo.getOfficeId());
    }

    void loadHomeData(){
        String timeLeft = "";
        if(!sharedPrefLoginInfo.isOrderedToday()){
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 12);
            calendar.set(Calendar.MINUTE, 0);
            long differ = TimeUtil.differ2(calendar.getTimeInMillis());
            if(differ > 0) {
                differ = differ/1000;
                if(differ>0 && differ<60) timeLeft = differ + " seconds left!";
                else if(differ>59 && differ<3600) timeLeft = differ/60 + " min left!";
                else timeLeft = differ/3600 + " hour left!";
            }
            else timeLeft = "Time's up!";
        }
        else timeLeft = "Confirmed!";

        getMvpView().onLoadHomeData(sharedPrefLoginInfo.isOrderedToday(), timeLeft, false, "");
    }

    void handleUserData(RemoteUser remoteUser){
        if(remoteUser.getSuccess() == AppConst.SUCCESS){
            int avatar = R.drawable.ic_male1;
            if(sharedPrefLoginInfo.getAvatar() == 1) avatar = R.drawable.ic_female1;

            getMvpView().onLoadData(avatar, remoteUser.getUser(), NetworkUtil.getLocalIpAddress());
        }
    }

    public void signOut(){
        sharedPrefLoginInfo.updateRemembered(false);
        getMvpView().onSignOut();
    }
}
