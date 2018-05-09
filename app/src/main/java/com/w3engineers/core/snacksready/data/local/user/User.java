package com.w3engineers.core.snacksready.data.local.user;
/*
 *  ****************************************************************************
 *  * Created by : Md. Hasnain on 4/27/2018 at 7:47 PM.
 *  * Email : ashik.pstu.cse@gmail.com
 *  *
 *  * Last edited by : Md. Hasnain on 4/27/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("sl")
    private String sl;
    @SerializedName("office_id")
    private String officeId;
    @SerializedName("name")
    private String name;
    @SerializedName("team")
    private String team;
    @SerializedName("lunch_confirmed")
    private boolean lunchConfirmed;

    /**
     * No args constructor for use in serialization
     *
     */
    public User() {
    }

    public User(String sl, String officeId, String name, String team, boolean lunchConfirmed) {
        super();
        this.sl = sl;
        this.officeId = officeId;
        this.name = name;
        this.team = team;
        this.lunchConfirmed = lunchConfirmed;
    }

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public boolean isLunchConfirmed() {
        return lunchConfirmed;
    }

    public void setLunchConfirmed(boolean lunchConfirmed) {
        this.lunchConfirmed = lunchConfirmed;
    }
}
