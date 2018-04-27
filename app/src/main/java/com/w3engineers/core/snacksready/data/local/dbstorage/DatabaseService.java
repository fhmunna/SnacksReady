package com.w3engineers.core.snacksready.data.local.dbstorage;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.w3engineers.core.snacksready.R;
import com.w3engineers.core.snacksready.data.local.snack.Snack;
import com.w3engineers.core.snacksready.data.local.snack.SnackDao;
import com.w3engineers.core.util.lib.roomdb.AppDatabase;

/*
* ****************************************************************************
* * Copyright © 2018 W3 Engineers Ltd., All rights reserved.
* *
* * Created by:
* * Name : Ahmed Mohmmad Ullah (Azim)
* * Date : 2/15/18
* * Email : azim@w3engineers.com
* *
* * Purpose: ROOM DB Services start class
* *
* * Last Edited by : Md. Hasnain on 04/03/18
* * History: Comment Added
* * 1: New table added
* * 2:
* *
* * Last Reviewed by : Md. Hasnain on 04/03/18
* ****************************************************************************
*/

@Database(entities = {
        Snack.class},
        version = 1, exportSchema = false)
public abstract class DatabaseService extends AppDatabase {

    //private static final String MIGRATION_SQL_1_2 = "ALTER TABLE  Employee ADD COLUMN address TEXT";
    /*
    public static AppDatabaseService on(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabaseService.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabaseService.class, context.getString(R.string.app_name))
                            .build();
                }
            }
        }

        return INSTANCE;
    }
    */
    private static volatile DatabaseService sInstance;
    private static volatile DatabaseService INSTANCE;

    // Create the database
    private static DatabaseService createOld(Context context) {
        RoomDatabase.Builder<DatabaseService> builder =
                Room.databaseBuilder(context, DatabaseService.class, context.getString(R.string.app_name));

        return builder.build();
    }

    // Get a database instance
    public static synchronized DatabaseService on() {
        return sInstance;
    }

    public static synchronized DatabaseService init(Context context) {

        if (sInstance == null) {
            synchronized (DatabaseService.class) {
                sInstance = createDb(context, context.getString(R.string.db_name), DatabaseService.class);
//                sInstance = createDb(context, context.getString(R.string.db_name), DatabaseService.class
//                        , MIGRATION_SQL_1_2);
            }
        }

        return sInstance;
    }

    public abstract SnackDao snackDao();
}