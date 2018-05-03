
package com.w3engineers.core.snacksready.ui.profile;


import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;

import com.w3engineers.core.snacksready.R;
import com.w3engineers.core.snacksready.data.local.user.User;
import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteUser;
import com.w3engineers.core.snacksready.databinding.FragmentProfileBinding;
import com.w3engineers.core.snacksready.ui.base.BaseFragment;
import com.w3engineers.core.snacksready.ui.main.MainActivity;
import com.w3engineers.core.snacksready.ui.splash.SplashActivity;
import com.w3engineers.core.util.helper.DialogUtil;
import com.w3engineers.core.util.helper.Toaster;
import com.w3engineers.core.util.lib.network.NetworkService;

public class ProfileFragment extends BaseFragment<ProfileMvpView, ProfilePresenter> implements ProfileMvpView, View.OnClickListener,
        DialogUtil.DialogButtonListener, NetworkService.ValidityCheckerCallBack{
    private String title;

    private FragmentProfileBinding fragmentProfileBinding;

    private AlertDialog loadingDialog;

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

        setClickListener(fragmentProfileBinding.btnConfirmLunch, fragmentProfileBinding.btnOrderSnack);
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.loadHomeData();

        NetworkService.setValidityCheckerCallBack(this);
        loadingDialog = DialogUtil.loadingDialogBuilder(getContext(), "Loading info");
        loadingDialog.setCancelable(true);
        loadingDialog.setCanceledOnTouchOutside(true);
        presenter.loadData();
    }

    @Override
    protected void stopUI() {
        NetworkService.removeValidityCheckerCallBack();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if(view == fragmentProfileBinding.btnConfirmLunch){
            MainActivity.runActivityWithFlag(getContext(), 1);
        }
        else if(view == fragmentProfileBinding.btnOrderSnack){
            MainActivity.runActivityWithFlag(getContext(), 0);
        }
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
    public void onLoadData(int avatar, User user, String ip) {
        getActivity().runOnUiThread(()->{
            fragmentProfileBinding.imgIcon.setImageResource(avatar);
            fragmentProfileBinding.txtName.setText(user.getName());
            fragmentProfileBinding.txtOfficeId.setText("Office Id: " + user.getOfficeId());
            fragmentProfileBinding.txtTeam.setText("Team: " + user.getTeam());
            fragmentProfileBinding.txtTeam.setVisibility(View.GONE);
            fragmentProfileBinding.txtIp.setText("IP Address: " + ip);
        });
    }

    @Override
    public void onLoadHomeData(boolean snackOrdered, String snacksMessage, boolean lunchOrdered, String lunchMessage) {
        getActivity().runOnUiThread(()->{
            fragmentProfileBinding.txtSnacksMsg.setText("Snacks: " + snacksMessage);
            if(snackOrdered) fragmentProfileBinding.btnOrderSnack.setText("View");

            if(lunchOrdered) fragmentProfileBinding.btnConfirmLunch.setText("View");
        });
    }

    @Override
    public void onSignOut() {
        getActivity().runOnUiThread(()->{
            Toaster.show("Signed out successfully!");
            SplashActivity.runActivity(getContext());
            getActivity().finish();
        });
    }

    @Override
    public void onResponse(RemoteUser remoteUser) {
        getActivity().runOnUiThread(()->{
            if(loadingDialog != null) loadingDialog.dismiss();
        });
        presenter.handleUserData(remoteUser);
    }

    @Override
    public void onFailure(String message) {
        getActivity().runOnUiThread(()->{
            if(loadingDialog != null) loadingDialog.dismiss();
            Toaster.show(message);
        });
    }
}
