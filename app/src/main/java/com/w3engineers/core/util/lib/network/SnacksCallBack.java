package com.w3engineers.core.util.lib.network;
/*
 *  ****************************************************************************
 *  * Created by : Md. Hasnain on 5/8/2018 at 3:04 PM.
 *  * Email : ashik.pstu.cse@gmail.com
 *  *
 *  * Last edited by : Md. Hasnain on 5/8/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteOrder;
import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteResponse;
import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteSnacks;

public interface SnacksCallBack {
    void onResponse(RemoteSnacks remoteSnacks);
    void onPlaceOrder(RemoteResponse remoteResponse);
    void onLoadOrder(RemoteOrder remoteOrder);
    void onFailure(String message);
}
