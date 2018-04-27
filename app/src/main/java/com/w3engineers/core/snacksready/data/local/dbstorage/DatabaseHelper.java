package com.w3engineers.core.snacksready.data.local.dbstorage;

import com.w3engineers.core.snacksready.data.local.snack.SnackService;

/*
* ****************************************************************************
* * Copyright © 2018 W3 Engineers Ltd., All rights reserved.
* *
* * Created by:
* * Name : Ahmed Mohmmad Ullah (Azim)
* * Date : 2/15/18
* * Email : azim@w3engineers.com
* *
* * Purpose: Enables injection of data sources.
* *
* * Last Edited by : Md. Hasnain on 04/03/18.
* * History: New db service provider added
* * 1:
* * 2:
* *
* * Last Reviewed by : Md. Hasnain on 04/03/18.
* ****************************************************************************
*/

public class DatabaseHelper {
    public static SnackService provideSnackService() {
        return new SnackService(DatabaseService.on().snackDao());
    }

}