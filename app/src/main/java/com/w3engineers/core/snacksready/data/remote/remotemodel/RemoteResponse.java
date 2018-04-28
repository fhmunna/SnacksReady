package com.w3engineers.core.snacksready.data.remote.remotemodel;

import com.google.gson.annotations.SerializedName;

public class RemoteResponse {
    @SerializedName("success")
    private Integer success;
    @SerializedName("message")
    private String message;

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
}
