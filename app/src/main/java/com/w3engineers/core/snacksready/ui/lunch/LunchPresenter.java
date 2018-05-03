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
import com.w3engineers.core.snacksready.ui.base.BasePresenter;

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
        //long oneDay = 24*60*60*1000;
        //long milSec = System.currentTimeMillis() - oneDay;
        //String date = TimeUtil.getDateFormat12(System.currentTimeMillis());
        //NetworkService.getSnacks();
        List<Lunch> lunchList = new ArrayList<>();

        for(int i = 0; i<10; i++){
            int j = i+1;
            String[]alternates = {"Alter 1", "Alter 2", "Alter 3"};
            Lunch lunch = new Lunch("Day "+j, "Lunch "+j, "Fixed "+j, alternates, "Image "+j);
            lunch.setId(j);

            lunchList.add(lunch);
        }

        getMvpView().onLunchListLoaded(lunchList);
    }

    public void confirmLunch(){
        //long oneDay = 24*60*60*1000;
        //long milSec = System.currentTimeMillis() - oneDay;
    }
}
