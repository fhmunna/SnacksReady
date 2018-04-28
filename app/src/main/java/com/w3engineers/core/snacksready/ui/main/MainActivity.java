package com.w3engineers.core.snacksready.ui.main;

import android.content.Context;
import android.content.Intent;

import com.w3engineers.core.snacksready.R;
import com.w3engineers.core.snacksready.databinding.ActivityMainBinding;
import com.w3engineers.core.snacksready.ui.base.BaseActivity;

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
