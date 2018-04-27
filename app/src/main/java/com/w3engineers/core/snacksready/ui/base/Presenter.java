package com.w3engineers.core.snacksready.ui.base;

import android.arch.lifecycle.Lifecycle;

/*
* ****************************************************************************
* * Copyright © 2018 W3 Engineers Ltd., All rights reserved.
* *
* * Created by:
* * Name : Anjan Debnath
* * Date : 10/25/17
* * Email : anjan@w3engineers.com
* *
* * Purpose: Every presenter in the app must either implement this interface or extend BasePresenter
* * indicating the MvpView type that wants to be attached with.
* *
* * Last Edited by : SUDIPTA KUMAR PAIK on 03/16/18.
* * History:
* * 1:
* * 2:
* *
* * Last Reviewed by : SUDIPTA KUMAR PAIK on 03/16/18.
* ****************************************************************************
*/

public interface Presenter<V extends MvpView> {

    /**
     * Called when MvpView type object attach to presenter
     *
     * @param mvpView The MvpView that have to attach.
     */
    void attachView(V mvpView);

    /**
     * Called when MvpView type object detach from presenter
     */
    void detachView();

    /**
     * Called when Lifecycle type object attach to presenter
     *
     * @param lifecycle The Lifecycle that have to attach.
     */
    void attachLifecycle(Lifecycle lifecycle);

    /**
     * Called when Lifecycle type object detach from presenter
     */
    void detachLifecycle(Lifecycle lifecycle);

    /**
     * Called when presenter type object creation done
     */
    void onPresenterCreated();

    /**
     * Called when presenter type object destroy done
     */
    void onPresenterDestroy();
}
