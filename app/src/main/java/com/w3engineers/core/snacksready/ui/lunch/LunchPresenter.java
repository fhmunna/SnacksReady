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
import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteLunchList;
import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteResponse;
import com.w3engineers.core.snacksready.ui.base.BasePresenter;
import com.w3engineers.core.util.helper.JsonUtil;
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
    }

    public void confirmLunch(List<Lunch> orderedLunch){
        String orderJson = JsonUtil.toJsonString(orderedLunch);
        NetworkService.saveOrderedLunch(sharedPrefLoginInfo.getOfficeId(), orderJson);
    }

    void handleRemoteLunchResponse(RemoteLunchList remoteLunchList){
        List<Lunch> lunchList = remoteLunchList.getLunchList();

        getMvpView().onLunchListLoaded(lunchList);
    }

    void handleRemoteLunchOrderConfirmationResponse(RemoteResponse remoteResponse){
        getMvpView().onLunchConfirmed(remoteResponse.getMessage(),
                remoteResponse.getSuccess() == AppConst.SUCCESS);
    }
}
