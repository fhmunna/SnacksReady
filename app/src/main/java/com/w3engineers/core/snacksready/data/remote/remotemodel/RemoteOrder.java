package com.w3engineers.core.snacksready.data.remote.remotemodel;

import com.google.gson.annotations.SerializedName;
import com.w3engineers.core.snacksready.data.local.snack.Snack;

public class RemoteOrder {
    @SerializedName("success")
    private Integer success;
    @SerializedName("message")
    private String message;
    @SerializedName("snack")
    private Snack snack;
    @SerializedName("ordered_by")
    private String orderedBy;

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

    public Snack getSnack() {
        return snack;
    }

    public void setSnack(Snack snack) {
        this.snack = snack;
    }

    public String getOrderedBy() {
        return orderedBy;
    }

    public void setOrderedBy(String orderedBy) {
        this.orderedBy = orderedBy;
    }
}
