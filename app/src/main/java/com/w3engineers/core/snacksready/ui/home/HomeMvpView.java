package com.w3engineers.core.snacksready.ui.home;

import com.w3engineers.core.snacksready.ui.base.MvpView;

public interface HomeMvpView extends MvpView {
    void onLoadLocalData(boolean isOrderSet, boolean isRemainderSet, String timeLeft);
}
