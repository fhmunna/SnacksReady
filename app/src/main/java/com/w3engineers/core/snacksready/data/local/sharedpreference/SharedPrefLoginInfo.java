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
    private static SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public SharedPrefLoginInfo(Context context) {
        this.context = context;

        if(preferences == null)
            preferences = context.getSharedPreferences(SharedPrefProp.SP_LOGIN, Context.MODE_PRIVATE);
    }

    public void storeLoginInfo(String officeId, int avatar, boolean isRemembered,
                               boolean isOrderedToday, boolean isAlarmSet) {
        editor = preferences.edit();
        editor.putString(SharedPrefProp.SP_OFFICE_ID, officeId);
        editor.putInt(SharedPrefProp.SP_AVATAR, avatar);
        editor.putBoolean(SharedPrefProp.SP_REMEMBERED, isRemembered);
        editor.putBoolean(SharedPrefProp.SP_ORDERED_TODAY, isOrderedToday);
        editor.putBoolean(SharedPrefProp.SP_ALARM_SET, isAlarmSet);
        editor.apply();
    }

    public String getOfficeId(){
        return preferences.getString(SharedPrefProp.SP_OFFICE_ID, "");
    }

    public int getAvatar(){
        return preferences.getInt(SharedPrefProp.SP_AVATAR, -1);
    }

    public boolean isRemembered(){
        return preferences.getBoolean(SharedPrefProp.SP_REMEMBERED, false);
    }

    public boolean isOrderedToday(){
        return preferences.getBoolean(SharedPrefProp.SP_ORDERED_TODAY, false);
    }

    public boolean isAlarmSet(){
        return preferences.getBoolean(SharedPrefProp.SP_ALARM_SET, false);
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

    public void updateOrderedToday(boolean orderedToday){
        editor = preferences.edit();
        editor.putBoolean(SharedPrefProp.SP_ORDERED_TODAY, orderedToday);
        editor.apply();
    }

    public void updateAlarmSet(boolean alarmSet){
        editor = preferences.edit();
        editor.putBoolean(SharedPrefProp.SP_ALARM_SET, alarmSet);
        editor.apply();
    }

    public void clearLoginInfo(){
        preferences = context.getSharedPreferences(SharedPrefProp.SP_LOGIN, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
