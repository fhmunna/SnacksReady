package com.w3engineers.core.snacksready.ui.base;

import android.arch.lifecycle.ViewModel;

/*
* ****************************************************************************
* * Copyright © 2018 W3 Engineers Ltd., All rights reserved.
* *
* * Created by:
* * Name : Anjan Debnath
* * Date : 10/25/17
* * Email : anjan@w3engineers.com
* *
* * Purpose: The ViewModel is automatically retained during configuration changes
* * so the data it holds is immediately available to the next activity or
* * fragment instance.
* * This will help us not to initialize the Presenter every time.
* * It will receive and return our presenter’s object.
* *
* * Last Edited by : SUDIPTA KUMAR PAIK on 03/16/18.
* * History:
* * 1:
* * 2:
* *
* * Last Reviewed by : SUDIPTA KUMAR PAIK on 03/16/18.
* ****************************************************************************
*/

public class BaseViewModel<V extends MvpView, P extends Presenter<V>> extends ViewModel {
    private P presenter;

    void setPresenter(P presenter) {
        if (this.presenter == null) {
            this.presenter = presenter;
        }
    }

    P getPresenter() {
        return this.presenter;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        presenter.onPresenterDestroy();
        presenter = null;
    }
}
