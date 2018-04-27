
package com.w3engineers.core.snacksready.ui.profile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.w3engineers.core.snacksready.R;
import com.w3engineers.core.snacksready.databinding.FragmentProfileBinding;
import com.w3engineers.core.snacksready.ui.base.BaseFragment;
import com.w3engineers.core.snacksready.ui.splash.SplashActivity;
import com.w3engineers.core.util.helper.DialogUtil;
import com.w3engineers.core.util.helper.Toaster;

public class ProfileFragment extends BaseFragment<ProfileMvpView, ProfilePresenter> implements ProfileMvpView,
        DialogUtil.DialogButtonListener{
    private String title;

    private FragmentProfileBinding fragmentProfileBinding;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String title) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    protected int getMenuId() {
        return R.menu.menu_profile;
    }

    @Override
    protected void startUI() {
        if (getArguments() != null) title = getArguments().getString("title");
        fragmentProfileBinding = (FragmentProfileBinding) getViewDataBinding();
    }

    @Override
    protected void stopUI() {

    }

    @Override
    protected ProfilePresenter initPresenter() {
        return new  ProfilePresenter();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_logout:
                DialogUtil.showConfirmationDialog(getContext(),
                        "Are you sure?", "",
                        "Sign Out", "Cancel", this, DialogUtil.FLAG_LOG_OUT);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickPositive(int flag) {
        if(flag == DialogUtil.FLAG_LOG_OUT) presenter.signOut();
    }

    @Override
    public void onCancel(int flag) {

    }

    @Override
    public void onClickNegative(int flag) {

    }

    @Override
    public void onSignOut() {
        getActivity().runOnUiThread(()->{
            Toaster.show("Signed out successfully!");
            SplashActivity.runActivity(getContext());
            getActivity().finish();
        });
    }
}
