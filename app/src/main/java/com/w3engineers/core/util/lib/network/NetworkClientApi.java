package com.w3engineers.core.util.lib.network;
/*
 *  ****************************************************************************
 *  * Created by : Md. Hasnain on 4/27/2018 at 6:26 PM.
 *  * Email : ashik.pstu.cse@gmail.com
 *  *
 *  * Last edited by : Md. Hasnain on 4/27/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteLunchList;
import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteOrder;
import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteResponse;
import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteSnacks;
import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteUser;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetworkClientApi {
    @GET("user_validity.php")
    Call<RemoteUser> checkUserValidity(@Query("office_id") String officeId);

    @GET("snacks.php")
    Call<RemoteSnacks> loadSnacks();

    @GET("order.php")
    Call<RemoteOrder> loadOrder(@Query("office_id") String officeId);

    @GET("save_order.php")
    Call<RemoteResponse> placeOrder(@Query("office_id") String officeId,
                                    @Query("snacks_id") int snacksId,
                                    @Query("ordered_by") String orderedBy);

    @GET("lunch.php")
    Call<RemoteLunchList> loadLunchList();

    @GET("save_lunch_order.php")
    Call<RemoteResponse> saveOrderedLunch(@Query("office_id") String officeId,
                                    @Query("lunch") String lunchJson,
                                    @Query("ordered_by") String orderedBy);

    @GET("lunch_ordered.php")
    Call<RemoteLunchList> getOrderedLunchList(@Query("office_id") String officeId);

    @GET("admin_validity.php")
    Call<RemoteResponse> checkAdminValidity(@Query("office_id") String officeId,
                                    @Query("pin") String pin);
}
