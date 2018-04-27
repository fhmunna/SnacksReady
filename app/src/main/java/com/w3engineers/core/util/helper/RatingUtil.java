package com.w3engineers.core.util.helper;

/*
 * ****************************************************************************
 * * Copyright © 2018 W3 Engineers Ltd., All rights reserved.
 * *
 * * Created by:
 * * Name : Md. Hasnain
 * * Date : 04/10/18 11.27PM
 * * Email : ashik.pstu.cse@gmail.com
 * *
 * * Purpose : Calculating rating of any scale
 * *
 * * Last Edited by : Md. Hasnain on 04/10/18.
 * * History:
 * * 1:
 * * 2:
 * *
 * * Last Reviewed by : Md. Hasnain on 04/10/18.
 * ****************************************************************************
 */

public class RatingUtil {
    private static final RatingUtil ourInstance = new RatingUtil();

    public static RatingUtil getInstance() {
        return ourInstance;
    }

    private RatingUtil() {

    }

    //using weighted average
    //each index of the rating array should contain the total ratings on that (index+1) scale
    public float calculateRating(long[] reviews){
        if(reviews == null || reviews.length == 0) return 0f;

        long totalRatings=0;
        long totalReviews=0;

        for (int i = 0; i<reviews.length; i++){
            totalRatings += (i+1)*reviews[i];
            totalReviews += reviews[i];
        }

        if(totalReviews == 0) return 0.f;

        return totalRatings/(float) totalReviews;
    }
}
