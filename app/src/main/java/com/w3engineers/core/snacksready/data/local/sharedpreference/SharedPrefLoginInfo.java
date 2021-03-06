package com.w3engineers.core.snacksready.data.local.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.w3engineers.core.snacksready.data.local.prefstorage.SharedPrefProp;

/*
*  ****************************************************************************
*  * Created by : Md. Hasnain on 4/2/2018 at 11:59 AM.
*  * Email : ashik.pstu.cse@gmail.com
*  * 
*  * Last edited by : Md. Hasnain on 04/04/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/
public class SharedPrefLoginInfo {
    private Context context;
    private SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public SharedPrefLoginInfo(Context context) {
        this.context = context;

        preferences = context.getSharedPreferences(SharedPrefProp.SP_LOGIN, Context.MODE_PRIVATE);
    }

    public void storeLoginInfo(String officeId, int avatar, boolean isRemembered) {
        editor = preferences.edit();
        editor.putString(SharedPrefProp.SP_OFFICE_ID, officeId);
        editor.putInt(SharedPrefProp.SP_AVATAR, avatar);
        editor.putBoolean(SharedPrefProp.SP_REMEMBERED, isRemembered);
        editor.apply();
    }

    public String getOfficeId(){
        return preferences.getString(SharedPrefProp.SP_OFFICE_ID, "");
    }

    public int getAvatar(){
        return preferences.getInt(SharedPrefProp.SP_AVATAR, 0);
    }

    public boolean isRemembered(){
        return preferences.getBoolean(SharedPrefProp.SP_REMEMBERED, false);
    }

    public void updateOfficeId(String newOfficeId){
        editor = preferences.edit();
        editor.putString(SharedPrefProp.SP_OFFICE_ID, newOfficeId);
        editor.apply();
    }

    public void updateAvatar(int avatar){
        editor = preferences.edit();
        editor.putInt(SharedPrefProp.SP_AVATAR, avatar);
        editor.apply();
    }

    public void updateRemembered(boolean remember){
        editor = preferences.edit();
        editor.putBoolean(SharedPrefProp.SP_REMEMBERED, remember);
        editor.apply();
    }

    public void clearLoginInfo(){
        preferences = context.getSharedPreferences(SharedPrefProp.SP_LOGIN, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
