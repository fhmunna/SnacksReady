package com.w3engineers.core.snacksready.ui.snacks;
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

import com.w3engineers.core.snacksready.data.local.snack.Snack;
import com.w3engineers.core.snacksready.ui.base.MvpView;

import java.util.List;

interface SnacksMvpView extends MvpView {
    void onSnacksLoaded(List<Snack> snacks);
    void onOrderLoaded(Snack snacks, String orderedBy);
    void onSnacksNotFound(String message);
    void onSnackConfirmed(String message, boolean success);
}
