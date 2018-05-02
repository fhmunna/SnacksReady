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
import com.w3engineers.core.util.lib.network.NetworkService;

public class ProfilePresenter extends BasePresenter<ProfileMvpView> {
    private SharedPrefLoginInfo sharedPrefLoginInfo;

    ProfilePresenter(){
        sharedPrefLoginInfo = PreferencesHelper.provideLoginInfoSharePrefService();
    }

    void loadData(){
        NetworkService.checkUserValidity(sharedPrefLoginInfo.getOfficeId());
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
