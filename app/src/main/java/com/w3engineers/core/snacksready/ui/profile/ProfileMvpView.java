package com.w3engineers.core.snacksready.ui.profile;
/*
 *  ****************************************************************************
 *  * Created by : Md. Hasnain on 4/24/2018 at 6:34 PM.
 *  * Email : ashik.pstu.cse@gmail.com
 *  *
 *  * Last edited by : Md. Hasnain on 4/24/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

import com.w3engineers.core.snacksready.data.local.user.User;
import com.w3engineers.core.snacksready.ui.base.MvpView;

public interface ProfileMvpView extends MvpView {
    void onLoadData(int avatar, User user, String ip);
    void onLoadHomeData(boolean snackOrdered, String snacksMessage, boolean lunchOrdered, String lunchMessage);
    void onSignOut();
}
