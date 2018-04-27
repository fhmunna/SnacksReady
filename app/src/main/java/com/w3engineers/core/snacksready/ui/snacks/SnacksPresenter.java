package com.w3engineers.core.snacksready.ui.snacks;
/*
 *  ****************************************************************************
 *  * Created by : Md. Hasnain on 4/24/2018 at 1:48 PM.
 *  * Email : ashik.pstu.cse@gmail.com
 *  *
 *  * Last edited by : Md. Hasnain on 4/24/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

import com.w3engineers.core.snacksready.data.local.snack.Snack;
import com.w3engineers.core.snacksready.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

public class SnacksPresenter extends BasePresenter<SnacksMvpView> {
    public void loadSnacks(){
        List<Snack> snacks = new ArrayList<>();
        for (int i = 1; i<10; i++){
            Snack s = new Snack("s "+i, 10+i, 5+i, 23, "");
            s.setId(i);
            snacks.add(s);
        }
        if(getMvpView() != null) getMvpView().onSnacksLoaded(snacks);
    }
}
