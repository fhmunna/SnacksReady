package com.w3engineers.core.snacksready.data.remote.remoteconst;
/*
 *  ****************************************************************************
 *  * Created by : Md. Hasnain on 4/27/2018 at 5:14 PM.
 *  * Email : ashik.pstu.cse@gmail.com
 *  *
 *  * Last edited by : Md. Hasnain on 4/27/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

public interface RemoteConst {
    String BASE_URL = "https://snacksready.000webhostapp.com/snacks_ready/";

    String BASE_API_PATH = BASE_URL + "api/";
    //String BASE_IMAGE_PATH = BASE_URL + "img/";

    String DB_NAME = "id5547164_db_snacks";
    String USERNAME = "id5547164_hasnain";
    String PASSWORD = "hasnain";

    String BASE_URL_LOCAL = "http://192.168.2.43/snacks_ready/";
    String BASE_API_PATH_LOCAL = BASE_URL_LOCAL + "api/";
    String BASE_IMAGE_PATH = BASE_URL_LOCAL + "img/";


    String ADMIN_BASE_URL_LOCAL = "http://192.168.2.43/W3_lunch_Project";
}
