package com.w3engineers.core.snacksready.data.local.snack;
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

@Entity(tableName = TableNames.TBL_SNACK, indices = {@Index(value = {ColumnNames.ID}, unique = true)})
public class Snack extends BaseObservable {
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ColumnNames.ID)
    @NonNull
    private int mId;
    @SerializedName("title")
    @ColumnInfo(name = ColumnNames.TITLE)
    private String mTitle;
    @SerializedName("order_count")
    @ColumnInfo(name = ColumnNames.ORDER_COUNT)
    private long mOrderCount;
    @SerializedName("review_count")
    @ColumnInfo(name = ColumnNames.REVIEW_COUNT)
    private long mReviewCount;
    @SerializedName("rating_sum")
    @ColumnInfo(name = ColumnNames.RATING_SUM)
    private long mRatingSum;
    @SerializedName("image")
    @ColumnInfo(name = ColumnNames.IMAGE)
    private String mImage;

    @Ignore
    private float mRating;
    @Ignore
    private boolean mIsOrdered;

    public Snack(String mTitle, long mOrderCount, long mReviewCount, long mRatingSum, String mImage) {
        this.mTitle = mTitle;
        this.mOrderCount = mOrderCount;
        this.mReviewCount = mReviewCount;
        this.mRatingSum = mRatingSum;
        this.mImage = mImage;
        this.mRating = 0f;
        this.mIsOrdered = false;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public long getOrderCount() {
        return mOrderCount;
    }

    public void setOrderCount(long orderCount) {
        this.mOrderCount = orderCount;
    }

    public long getReviewCount() {
        return mReviewCount;
    }

    public void setReviewCount(long mReviewCount) {
        this.mReviewCount = mReviewCount;
    }

    public long getRatingSum() {
        return mRatingSum;
    }

    public void setRatingSum(long ratingSum) {
        this.mRatingSum = ratingSum;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        this.mImage = image;
    }

    public float getRating() {
        return mRating;
    }

    public void setRating(float rating) {
        this.mRating = rating;
    }

    public boolean isOrdered() {
        return mIsOrdered;
    }

    public void setOrdered(boolean isOrdered) {
        this.mIsOrdered = isOrdered;
    }
}
