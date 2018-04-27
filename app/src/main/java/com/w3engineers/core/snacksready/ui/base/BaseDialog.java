package com.w3engineers.core.snacksready.ui.base;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*
* ****************************************************************************
* * Copyright © 2018 W3 Engineers Ltd., All rights reserved.
* *
* * Created by:
* * Name : Azizul Islam
* * Date : 10/16/17
* * Email : azizul@w3engineers.com
* *
* * Purpose: Abstract Base Dialog that every custom dialog class in this application must extends.
* *
* * Last Edited by : SUDIPTA KUMAR PAIK on 03/16/18.
* * History:
* * 1:
* * 2:
* *
* * Last Reviewed by : SUDIPTA KUMAR PAIK on 03/16/18.
* ****************************************************************************
*/

public abstract class BaseDialog<V extends MvpView, P extends BasePresenter<V>> extends DialogFragment implements MvpView{
    private P presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        int viewId = getLayoutId();
        presenter = initPresenter();
        presenter.attachView((V)this);
        View view = inflater.inflate(viewId, container, false);
        startUi();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    protected abstract int getLayoutId();
    protected abstract P initPresenter();
    protected abstract void startUi();
}
