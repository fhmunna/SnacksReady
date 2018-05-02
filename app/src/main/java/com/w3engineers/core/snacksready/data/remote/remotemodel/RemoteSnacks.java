package com.w3engineers.core.snacksready.data.remote.remotemodel;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.w3engineers.core.snacksready.data.local.snack.Snack;

public class RemoteSnacks {
    @SerializedName("success")
    private Integer success;
    @SerializedName("message")
    private String message;
    @SerializedName("snacks")
    private List<Snack> snacks = null;

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

    public List<Snack> getSnacks() {
        return snacks;
    }

    public void setSnacks(List<Snack> snacks) {
        this.snacks = snacks;
    }

}