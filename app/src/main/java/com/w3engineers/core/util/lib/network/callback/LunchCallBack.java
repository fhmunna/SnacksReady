package com.w3engineers.core.util.lib.network.callback;
/*
 *  ****************************************************************************
 *  * Created by : Md. Hasnain on 5/8/2018 at 2:00 PM.
 *  * Email : ashik.pstu.cse@gmail.com
 *  *
 *  * Last edited by : Md. Hasnain on 5/8/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

import com.w3engineers.core.snacksready.data.local.lunch.Lunch;
import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteLunchList;
import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteResponse;

public interface LunchCallBack {
    void onResponse(RemoteLunchList remoteLunchList);
    void onConfirmLunch(RemoteResponse remoteResponse);
    void onLoadLunch(Lunch lunch);
    void onFailure(String message);
}
