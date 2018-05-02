package com.w3engineers.core.snacksready.ui.snacks;
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

public class SnacksPresenter extends BasePresenter<SnacksMvpView> {
    private SharedPrefLoginInfo sharedPrefLoginInfo;

    SnacksPresenter(){
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
        NetworkService.getSnacks();
    }

    public void handleResponse(RemoteSnacks remoteSnacks){
        if(remoteSnacks.getSuccess() == AppConst.SUCCESS){
            getMvpView().onSnacksLoaded(remoteSnacks.getSnacks());
        }
        else {
            getMvpView().onSnacksNotFound(remoteSnacks.getMessage());
        }
    }

    public void confirmSnack(Snack snack){
        //long oneDay = 24*60*60*1000;
        //long milSec = System.currentTimeMillis() - oneDay;
        NetworkService.placeOrder(TimeUtil.getDateFormat12(System.currentTimeMillis()),
                sharedPrefLoginInfo.getOfficeId(), snack.getId(), NetworkUtil.getLocalIpAddress());
    }

    public void handleOrderPlacementResponse(RemoteResponse remoteResponse){
        String msg = remoteResponse.getMessage();
        boolean success = remoteResponse.getSuccess() == AppConst.SUCCESS;

        if(remoteResponse.getSuccess() == AppConst.SUCCESS) {
            sharedPrefLoginInfo.updateOrderedToday(true);
            NetworkService.loadOrder(sharedPrefLoginInfo.getOfficeId());
            msg = "Order placed successfully from IP " + NetworkUtil.getLocalIpAddress();
        }

        getMvpView().onSnackConfirmed(msg, success);

    }

    public void handleLoadedOrder(RemoteOrder remoteOrder){
        getMvpView().onOrderLoaded(remoteOrder.getSnack(), remoteOrder.getOrderedBy());
    }
}
