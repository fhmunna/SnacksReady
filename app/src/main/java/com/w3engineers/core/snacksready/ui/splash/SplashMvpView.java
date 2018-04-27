
/*
*  ****************************************************************************
*  * Created by : Azizul Islam process 13-Oct-17 at 4:02 PM.
*  * Email : azizul@w3engineers.com
*  *
*  * Last edited by : Azizul Islam process 13-Oct-17.
*  *
*  * Last Reviewed by : <Reviewer Name> process <mm/dd/yy>
*  ****************************************************************************
*/
package com.w3engineers.core.snacksready.ui.splash;

import com.w3engineers.core.snacksready.ui.base.MvpView;

public interface SplashMvpView extends MvpView {
    void onNewSignIn();
    void onValidSignIn();
    void onInvalidSignIn();
    void onForgot();
}