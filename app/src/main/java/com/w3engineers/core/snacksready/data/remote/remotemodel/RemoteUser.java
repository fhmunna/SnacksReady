package com.w3engineers.core.snacksready.data.remote.remotemodel;
/*
 *  ****************************************************************************
 *  * Created by : Md. Hasnain on 4/27/2018 at 7:42 PM.
 *  * Email : ashik.pstu.cse@gmail.com
 *  *
 *  * Last edited by : Md. Hasnain on 4/27/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

import com.google.gson.annotations.SerializedName;
import com.w3engineers.core.snacksready.data.local.user.User;

import java.util.List;

public class RemoteUser {
    @SerializedName("success")
    private Integer success;
    @SerializedName("message")
    private String message;
    @SerializedName("user")
    private User user = null;
    @SerializedName("ordered_today")
    private boolean orderedToday;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isOrderedToday() {
        return orderedToday;
    }

    public void setOrderedToday(boolean orderedToday) {
        this.orderedToday = orderedToday;
    }
}
