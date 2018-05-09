package com.w3engineers.core.util.lib.network.callback;
/*
 *  ****************************************************************************
 *  * Created by : Md. Hasnain on 5/8/2018 at 3:05 PM.
 *  * Email : ashik.pstu.cse@gmail.com
 *  *
 *  * Last edited by : Md. Hasnain on 5/8/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteUser;

public interface ValidityCheckerCallBack{
    void onResponse(RemoteUser remoteUser);
    void onFailure(String message);
}
