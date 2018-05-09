package com.w3engineers.core.util.helper;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

/*
 * ****************************************************************************
 * * Copyright © 2018 W3 Engineers Ltd., All rights reserved.
 * *
 * * Created by:
 * * Name : Md. Hasnain
 * * Date : 04/09/18
 * * Email : ashik.pstu.cse@gmail.com
 * *
 * * Purpose : Performing Object->JSON and JSON->Object conversion
 * *
 * * Last Edited by : Md. Hasnain on 04/09/18.
 * * History:
 * * 1:
 * * 2:
 * *
 * * Last Reviewed by : Md. Hasnain on 04/09/18.
 * ****************************************************************************
 */

public class JsonUtil {
    private static final JsonUtil ourInstance = new JsonUtil();

    public static JsonUtil getInstance() {
        return ourInstance;
    }

    private JsonUtil() {
    }

    public static String toJsonString(Object object){
        return new Gson().toJson(object);
    }

    public static Object fromJsonToObject(String json, Type type){
        return new Gson().fromJson(json, type);
    }

    public static String toJsonString(List<Object> objectList){
        return new Gson().toJson(objectList);
    }
}
