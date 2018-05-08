package com.w3engineers.core.snacksready.data.remote.remotemodel;

import com.google.gson.annotations.SerializedName;
import com.w3engineers.core.snacksready.data.local.lunch.Lunch;
import com.w3engineers.core.snacksready.data.local.snack.Snack;

import java.util.List;

public class RemoteLunchList {
    @SerializedName("success")
    private Integer success;
    @SerializedName("message")
    private String message;
    @SerializedName("lunch")
    private List<Lunch> lunchList = null;

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

    public List<Lunch> getLunchList() {
        return lunchList;
    }

    public void setLunchList(List<Lunch> lunchList) {
        this.lunchList = lunchList;
    }
}