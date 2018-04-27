package com.w3engineers.core.util.lib.roomdb;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/*
* ****************************************************************************
* * Copyright © 2018 W3 Engineers Ltd., All rights reserved.
* *
* * Created by:
* * Name : SUDIPTA KUMAR PAIK
* * Date : 2/1/18
* * Email : sudipta@w3engineers.com
* *
* * Purpose : Room Database Interface
* *
* * Last Edited by : SUDIPTA KUMAR PAIK on 2/1/18.
* * History:
* * 1:
* * 2:
* *
* * Last Reviewed by : SUDIPTA KUMAR PAIK on 2/1/18.
* ****************************************************************************
*/

public abstract class AppDatabase extends RoomDatabase {
    protected static <T extends RoomDatabase> T createDb(Context context, String dbName, Class<T> dbService, String... migrationScripts) {
        RoomDatabase.Builder<T> builder = Room.databaseBuilder(context, dbService, dbName);

        for (Migration migration : getMigrations(migrationScripts)) {
            builder.addMigrations(migration);
        }

        return builder.build();
    }

    private static List<Migration> getMigrations(String... migrationScripts) {
        List<Migration> migrationList = new ArrayList<>();

        int startVersion = 1;
        int endVersion = 2;

        Migration migration;

        for (final String migrationSchema : migrationScripts) {
            migration = new Migration(startVersion, endVersion) {
                @Override
                public void migrate(@NonNull SupportSQLiteDatabase database) {
                    database.execSQL(migrationSchema);
                }
            };

            startVersion++;
            endVersion++;

            migrationList.add(migration);
        }

        return migrationList;
    }
}