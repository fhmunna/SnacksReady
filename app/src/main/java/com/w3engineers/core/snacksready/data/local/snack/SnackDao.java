package com.w3engineers.core.snacksready.data.local.snack;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.w3engineers.core.snacksready.data.local.dbstorage.ColumnNames;
import com.w3engineers.core.snacksready.data.local.dbstorage.TableNames;

import java.util.List;

/*
* ****************************************************************************
* * Copyright © 2018 W3 Engineers Ltd., All rights reserved.
* *
* * Created by:
* * Name : Md. Hasnain
* * Date : 04/03/18
* * Email : ashik.pstu.cse@gmail.com
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
@Dao
public interface SnackDao {
    // Select all from snack table
    @Query("SELECT * FROM " + TableNames.TBL_SNACK)
    List<Snack> getAllSnackList();

    // Select all from snack table
    @Query("SELECT * FROM " + TableNames.TBL_SNACK)
    LiveData<List<Snack>> getAllSnacks();

    // Select one task from snack table by id
    @Query("SELECT * FROM " + TableNames.TBL_SNACK + " WHERE " + ColumnNames.ID + " = :id")
    Snack getSnackById(long id);

    /**
     * Insert a snack in the database. If the snack already exists, replace it.
     *
     * @param snack the user to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Snack snack);

    @Update
    int updateSnack(Snack snack);

    /**
     * Delete all snack.
     */
    @Query("DELETE FROM " + TableNames.TBL_SNACK)
    void deleteAllSnacks();

    @Delete
    void deleteItem(Snack snack);

    /**
     * Delete content by id.
     */
    @Query("DELETE FROM " + TableNames.TBL_SNACK + " WHERE " + ColumnNames.ID + " = :id")
    void deleteItem(String id);
}