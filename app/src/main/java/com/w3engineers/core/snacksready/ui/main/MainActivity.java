package com.w3engineers.core.snacksready.ui.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.w3engineers.core.snacksready.R;
import com.w3engineers.core.snacksready.data.local.appconst.AppConst;
import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteSnacks;
import com.w3engineers.core.snacksready.databinding.ActivityMainBinding;
import com.w3engineers.core.snacksready.ui.base.BaseActivity;
import com.w3engineers.core.util.helper.DialogUtil;
import com.w3engineers.core.util.helper.TimeUtil;
import com.w3engineers.core.util.helper.Toaster;
import com.w3engineers.core.util.lib.network.NetworkService;

public class MainActivity extends BaseActivity<MainMvpView, MainPresenter> implements MainMvpView {
    private ActivityMainBinding activityMainBinding;

    private PagerAdapter pagerAdapter;

    public static void runActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);

        runCurrentActivity(context, intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void startUI() {
        activityMainBinding = (ActivityMainBinding)getViewDataBinding();
        setSupportActionBar(activityMainBinding.toolbar);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        activityMainBinding.viewPager.setAdapter(pagerAdapter);
        activityMainBinding.viewPagerTab.setViewPager(activityMainBinding.viewPager);
    }

    @Override
    protected void stopUI() {
    }

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter();
    }
}
