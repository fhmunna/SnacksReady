package com.w3engineers.core.util.lib.network;
/*
 *  ****************************************************************************
 *  * Created by : Md. Hasnain on 4/27/2018 at 6:30 PM.
 *  * Email : ashik.pstu.cse@gmail.com
 *  *
 *  * Last edited by : Md. Hasnain on 4/27/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

import android.support.annotation.NonNull;
import android.util.Log;

import com.w3engineers.core.snacksready.data.remote.remoteconst.RemoteConst;
import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteOrder;
import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteResponse;
import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteSnacks;
import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static Retrofit retrofit;
    private static NetworkClientApi networkClientApi;
    private static ValidityCheckerCallBack mValidityListener;
    private static SnacksCallBack mSnacksListener;

    public static void start() {
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(RemoteConst.BASE_API_PATH_LOCAL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        if(networkClientApi ==null) networkClientApi = retrofit.create(NetworkClientApi.class);
    }

    public static void setValidityCheckerCallBack(ValidityCheckerCallBack validityCheckerCallBack){
        mValidityListener = validityCheckerCallBack;
    }

    public static void removeValidityCheckerCallBack(){
        mValidityListener = null;
    }

    public static void setSnacksCallBack(SnacksCallBack snacksCallBack){
        mSnacksListener = snacksCallBack;
    }

    public static void removeSnacksCallBack(){
        mSnacksListener = null;
    }

    public static void checkUserValidity(String officeId){
        Call<RemoteUser> call = networkClientApi.checkUserValidity(officeId);
        call.enqueue(new Callback<RemoteUser>() {
            @Override
            public void onResponse(@NonNull Call<RemoteUser> call, @NonNull Response<RemoteUser> response) {
                if (response.isSuccessful()) {
                    RemoteUser remoteUser = response.body();
                    Log.d(NetworkService.class.getSimpleName(), remoteUser.getMessage());
                    if(mValidityListener != null) mValidityListener.onResponse(remoteUser);
                } else {
                    Log.d(NetworkService.class.getSimpleName(), response.errorBody().toString());
                    if(mValidityListener != null) mValidityListener.onFailure("Failed to get data. May be Api problem.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<RemoteUser> call, @NonNull Throwable t) {
                Log.d(NetworkService.class.getSimpleName(), "Failed::" + t.toString());
                if(mValidityListener != null) mValidityListener.onFailure(t.getMessage());
            }
        });
    }

    public static void getSnacks(){
        Call<RemoteSnacks> call = networkClientApi.loadSnacks();
        call.enqueue(new Callback<RemoteSnacks>() {
            @Override
            public void onResponse(@NonNull Call<RemoteSnacks> call, @NonNull Response<RemoteSnacks> response) {
                if (response.isSuccessful()) {
                    RemoteSnacks remoteSnacks = response.body();
                    Log.d(NetworkService.class.getSimpleName(), remoteSnacks.getMessage());
                    if(mSnacksListener != null) mSnacksListener.onResponse(remoteSnacks);
                } else {
                    Log.d(NetworkService.class.getSimpleName(), response.errorBody().toString());
                    if(mSnacksListener != null) mSnacksListener.onFailure("Failed to get data. May be Api problem.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<RemoteSnacks> call, @NonNull Throwable t) {
                Log.d(NetworkService.class.getSimpleName(), "Failed::" + t.toString());
                if(mSnacksListener != null) mSnacksListener.onFailure(t.getMessage());
            }
        });
    }

    public static void placeOrder(String officeId, int snacksId, String ordered_by){
        Call<RemoteResponse> call = networkClientApi.placeOrder(officeId, snacksId, ordered_by);
        call.enqueue(new Callback<RemoteResponse>() {
            @Override
            public void onResponse(@NonNull Call<RemoteResponse> call, @NonNull Response<RemoteResponse> response) {
                if (response.isSuccessful()) {
                    RemoteResponse remoteResponse = response.body();
                    Log.d(NetworkService.class.getSimpleName(), remoteResponse.getMessage());
                    if(mSnacksListener != null) mSnacksListener.onPlaceOrder(remoteResponse);
                } else {
                    Log.d(NetworkService.class.getSimpleName(), response.errorBody().toString());
                    if(mSnacksListener != null) mSnacksListener.onFailure("Failed to get data. May be Api problem.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<RemoteResponse> call, @NonNull Throwable t) {
                Log.d(NetworkService.class.getSimpleName(), "Failed::" + t.toString());
                if(mSnacksListener != null) mSnacksListener.onFailure(t.getMessage());
            }
        });
    }

    public static void loadOrder(String officeId){
        Call<RemoteOrder> call = networkClientApi.loadOrder(officeId);
        call.enqueue(new Callback<RemoteOrder>() {
            @Override
            public void onResponse(@NonNull Call<RemoteOrder> call, @NonNull Response<RemoteOrder> response) {
                if (response.isSuccessful()) {
                    RemoteOrder remoteOrder = response.body();
                    Log.d(NetworkService.class.getSimpleName(), remoteOrder.getMessage());
                    if(mSnacksListener != null) mSnacksListener.onLoadOrder(remoteOrder);
                } else {
                    Log.d(NetworkService.class.getSimpleName(), response.errorBody().toString());
                    if(mSnacksListener != null) mSnacksListener.onFailure("Failed to get data. May be Api problem.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<RemoteOrder> call, @NonNull Throwable t) {
                Log.d(NetworkService.class.getSimpleName(), "Failed::" + t.toString());
                if(mSnacksListener != null) mSnacksListener.onFailure(t.getMessage());
            }
        });
    }

    public interface ValidityCheckerCallBack{
        void onResponse(RemoteUser remoteUser);
        void onFailure(String message);
    }

    public interface SnacksCallBack{
        void onResponse(RemoteSnacks remoteSnacks);
        void onPlaceOrder(RemoteResponse remoteResponse);
        void onLoadOrder(RemoteOrder remoteOrder);
        void onFailure(String message);
    }
}
