package com.w3engineers.core.snacksready.data.local.snack;

import android.arch.lifecycle.LiveData;

import com.w3engineers.core.util.helper.Logger;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/*
* ****************************************************************************
* * Copyright © 2018 W3 Engineers Ltd., All rights reserved.
* *
* * Created by:
* * Name : SUDIPTA KUMAR PAIK
* * Date : 2/13/18
* * Email : sudipta@w3engineers.com
* *
* * Purpose :
* *
* * Last Edited by : Md. Hasnain on 04/03/18.
* * History:
* * 1:
* * 2:
* *  
* * Last Reviewed by : Md. Hasnain on 04/03/18.
* ****************************************************************************
*/
public class SnackService {
    private final SnackDao mSnackDao;

    public SnackService(SnackDao snackDao) {
        mSnackDao = snackDao;
    }

    public Snack getSnackById(long id) {
        return mSnackDao.getSnackById(id);
    }

    public long insert(Snack snack) {
        return mSnackDao.insert(snack);
    }

    public int updateSnack(Snack snack) {
        int value = mSnackDao.updateSnack(snack);
        Logger.log("Updated value", value + "");
        return value;
    }

    public void deleteAllSnacks() {
        mSnackDao.deleteAllSnacks();
    }

    public void deleteItem(Snack snack) {
        mSnackDao.deleteItem(snack);
    }

    public List<Snack> getSnacks() {
        return mSnackDao.getAllSnackList();
    }

    public LiveData<List<Snack>> getAllSnackLiveData() {
        return mSnackDao.getAllSnacks();
    }
}
