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

    /**
     * No args constructor for use in serialization
     *
     */
    public RemoteSnacks() {
    }

    /**
     *
     * @param message
     * @param snacks
     * @param success
     */
    public RemoteSnacks(Integer success, String message, List<Snack> snacks) {
        super();
        this.success = success;
        this.message = message;
        this.snacks = snacks;
    }

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