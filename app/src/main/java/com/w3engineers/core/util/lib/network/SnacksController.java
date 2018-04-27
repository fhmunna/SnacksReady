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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.w3engineers.core.snacksready.data.local.user.User;
import com.w3engineers.core.snacksready.data.remote.remoteconst.RemoteDirConst;
import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteSnacks;
import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteUser;
import com.w3engineers.core.util.helper.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SnacksController{
    private static Retrofit retrofit;
    private static SnacksApi snacksApi;

    public void start() {
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(RemoteDirConst.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        if(snacksApi==null) snacksApi = retrofit.create(SnacksApi.class);
    }

    public void checkUserValidity(String officeId){
        Call<RemoteUser> call = snacksApi.checkUserValidity(officeId);
        call.enqueue(new Callback<RemoteUser>() {
            @Override
            public void onResponse(@NonNull Call<RemoteUser> call, @NonNull Response<RemoteUser> response) {
                if (response.isSuccessful()) {
                    RemoteUser remoteUser = response.body();
                } else {
                    Log.d(SnacksController.class.getSimpleName(), response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<RemoteUser> call, @NonNull Throwable t) {
                Log.d(SnacksController.class.getSimpleName(), "Failed::" + t.toString());
            }
        });
    }

    public void getSnacks(){
        Call<RemoteSnacks> call = snacksApi.loadSnacks();
        call.enqueue(new Callback<RemoteSnacks>() {
            @Override
            public void onResponse(@NonNull Call<RemoteSnacks> call, @NonNull Response<RemoteSnacks> response) {
                if (response.isSuccessful()) {
                    RemoteSnacks remoteSnacks = response.body();
                    Log.d(SnacksController.class.getSimpleName(), remoteSnacks.getSnacks().get(0).getTitle());
                } else {
                    Log.d(SnacksController.class.getSimpleName(), response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<RemoteSnacks> call, @NonNull Throwable t) {
                Log.d(SnacksController.class.getSimpleName(), "Failed::" + t.toString());
            }
        });
    }
}
