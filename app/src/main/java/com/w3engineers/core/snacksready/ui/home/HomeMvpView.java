package com.w3engineers.core.snacksready.ui.home;

import com.w3engineers.core.snacksready.ui.base.MvpView;

public interface HomeMvpView extends MvpView {
    void onLoadLocalData(boolean isSnacksOrdered, boolean isLunchConfirmed, boolean isRemainderSet,
                         String timeLeftSnacks, String badgeLunch);
}
