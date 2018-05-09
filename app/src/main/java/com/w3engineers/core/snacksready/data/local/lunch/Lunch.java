package com.w3engineers.core.snacksready.data.local.lunch;
/*
 *  ****************************************************************************
 *  * Created by : Md. Hasnain on 4/24/2018 at 11:12 AM.
 *  * Email : ashik.pstu.cse@gmail.com
 *  *
 *  * Last edited by : Md. Hasnain on 4/24/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.w3engineers.core.snacksready.data.local.dbstorage.ColumnNames;
import com.w3engineers.core.snacksready.data.local.dbstorage.TableNames;

@Entity(tableName = TableNames.TBL_LUNCH, indices = {@Index(value = {ColumnNames.ID}, unique = true)})
public class Lunch extends BaseObservable {
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ColumnNames.ID)
    @NonNull
    private int mId;
    @SerializedName("date")
    @ColumnInfo(name = ColumnNames.DATE)
    private String mDate;
    @SerializedName("title")
    @ColumnInfo(name = ColumnNames.TITLE)
    private String mTitle;
    @SerializedName("fixed_menu")
    @ColumnInfo(name = ColumnNames.FIXED_MENU)
    private String mFixedMenu;
    @SerializedName("alternate_menu")
    @ColumnInfo(name = ColumnNames.ALTERNATE_MENU)
    private String mAlternateMenu;

    @Ignore
    private String mSelectedAlterMenu = "";
    @Ignore
    private boolean mIsOrdered;

    public Lunch(String mDate, String mTitle, String mFixedMenu, String mAlternateMenu) {
        this.mDate = mDate;
        this.mTitle = mTitle;
        this.mFixedMenu = mFixedMenu;
        this.mAlternateMenu = mAlternateMenu;
        this.mSelectedAlterMenu = "";
        this.mIsOrdered = false;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        this.mDate = date;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getFixedMenu() {
        return mFixedMenu;
    }

    public void setFixedMenu(String fixedMenu) {
        this.mFixedMenu = fixedMenu;
    }

    public String getAlternateMenu() {
        return mAlternateMenu;
    }

    public void setAlternateMenu(String alternateMenu) {
        this.mAlternateMenu = alternateMenu;
    }

    public String getSelectedAlterMenu() {
        return mSelectedAlterMenu;
    }

    public void setSelectedAlterMenu(String mSelectedAlterMenu) {
        this.mSelectedAlterMenu = mSelectedAlterMenu;
    }

    public boolean isOrdered() {
        return mIsOrdered;
    }

    public void setOrdered(boolean isOrdered) {
        this.mIsOrdered = isOrdered;
    }
}
