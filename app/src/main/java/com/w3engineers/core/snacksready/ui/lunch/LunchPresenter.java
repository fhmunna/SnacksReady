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

import com.w3engineers.core.snacksready.data.local.appconst.AppConst;
import com.w3engineers.core.snacksready.data.local.lunch.Lunch;
import com.w3engineers.core.snacksready.data.local.prefstorage.PreferencesHelper;
import com.w3engineers.core.snacksready.data.local.sharedpreference.SharedPrefLoginInfo;
import com.w3engineers.core.snacksready.data.local.snack.Snack;
import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteOrder;
import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteResponse;
import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteSnacks;
import com.w3engineers.core.snacksready.ui.base.BasePresenter;
import com.w3engineers.core.util.helper.NetworkUtil;
import com.w3engineers.core.util.helper.TimeUtil;
import com.w3engineers.core.util.lib.network.NetworkService;

import java.util.ArrayList;
import java.util.List;

public class LunchPresenter extends BasePresenter<LunchMvpView> {
    private SharedPrefLoginInfo sharedPrefLoginInfo;

    LunchPresenter(){
        sharedPrefLoginInfo = PreferencesHelper.provideLoginInfoSharePrefService();
    }

    public void whatToLoad(){
        if(sharedPrefLoginInfo.isOrderedToday()){
            NetworkService.loadOrder(sharedPrefLoginInfo.getOfficeId());
        }
        else loadSnacks();
    }

    void loadSnacks(){
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

    public void handleResponse(RemoteSnacks remoteSnacks){
        if(remoteSnacks.getSuccess() == AppConst.SUCCESS){
            //getMvpView().onLunchListLoaded(remoteSnacks.getSnacks());
        }
        else {
            getMvpView().onLunchNotFound(remoteSnacks.getMessage());
        }
    }

    public void confirmLunch(){
        //long oneDay = 24*60*60*1000;
        //long milSec = System.currentTimeMillis() - oneDay;
    }

    public void handleOrderPlacementResponse(RemoteResponse remoteResponse){
        String msg = remoteResponse.getMessage();
        boolean success = remoteResponse.getSuccess() == AppConst.SUCCESS;

        if(remoteResponse.getSuccess() == AppConst.SUCCESS) {
            sharedPrefLoginInfo.updateOrderedToday(true);
            NetworkService.loadOrder(sharedPrefLoginInfo.getOfficeId());
            msg = "Order placed successfully from IP " + NetworkUtil.getLocalIpAddress();
        }

        getMvpView().onLunchConfirmed(msg, success);

    }

    public void handleLoadedOrder(RemoteOrder remoteOrder){
        //getMvpView().onOrderLoaded(remoteOrder.getSnack(), remoteOrder.getOrderedBy());
    }
}
