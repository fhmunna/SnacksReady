package com.w3engineers.core.snacksready.ui.lunch;
/*
 *  ****************************************************************************
 *  * Created by : Md. Hasnain on 4/24/2018 at 1:48 PM.
 *  * Email : ashik.pstu.cse@gmail.com
 *  *
 *  * Last edited by : Md. Hasnain on 4/24/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

import com.w3engineers.core.snacksready.data.local.lunch.Lunch;
import com.w3engineers.core.snacksready.data.local.prefstorage.PreferencesHelper;
import com.w3engineers.core.snacksready.data.local.sharedpreference.SharedPrefLoginInfo;
import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteLunchList;
import com.w3engineers.core.snacksready.ui.base.BasePresenter;
import com.w3engineers.core.util.lib.network.NetworkService;

import java.util.ArrayList;
import java.util.List;

public class LunchPresenter extends BasePresenter<LunchMvpView> {
    private SharedPrefLoginInfo sharedPrefLoginInfo;

    LunchPresenter(){
        sharedPrefLoginInfo = PreferencesHelper.provideLoginInfoSharePrefService();
    }

    public void whatToLoad(){
        loadLunch();
    }

    void loadLunch(){
        NetworkService.getLunchList();
        List<Lunch> lunchList = new ArrayList<>();

        for(int i = 1; i<=15; i++){
            String alternates = "Alter 1,Alter 2,Alter 3";
            if(i>10) alternates = "Alter 1,Alter 2";
            Lunch lunch = new Lunch("Day "+i, "Lunch "+i, "Fixed "+i, alternates);
            lunch.setId(i);

            lunchList.add(lunch);
        }

        getMvpView().onLunchListLoaded(lunchList);
    }

    public void confirmLunch(){
        //long oneDay = 24*60*60*1000;
        //long milSec = System.currentTimeMillis() - oneDay;
    }

    void handleRemoteLunchResponse(RemoteLunchList remoteLunchList){
        List<Lunch> lunchList = remoteLunchList.getLunchList();

        getMvpView().onLunchListLoaded(lunchList);
    }
}
