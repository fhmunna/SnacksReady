package com.w3engineers.core.snacksready.ui.lunch;
/*
 *  ****************************************************************************
 *  * Created by : Md. Hasnain on 4/24/2018 at 1:47 PM.
 *  * Email : ashik.pstu.cse@gmail.com
 *  *
 *  * Last edited by : Md. Hasnain on 4/24/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

import com.w3engineers.core.snacksready.data.local.lunch.Lunch;
import com.w3engineers.core.snacksready.data.local.snack.Snack;
import com.w3engineers.core.snacksready.ui.base.MvpView;

import java.util.List;

interface LunchMvpView extends MvpView {
    void onLunchListLoaded(List<Lunch> lunchList);
    void onOrderLoaded(Lunch lunch, String orderedBy);
    void onLunchNotFound(String message);
    void onLunchConfirmed(String message, boolean success);
}
