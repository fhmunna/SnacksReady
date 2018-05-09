package com.w3engineers.core.snacksready.ui.admin;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;

import com.thefinestartist.finestwebview.FinestWebView;
import com.w3engineers.core.snacksready.R;
import com.w3engineers.core.snacksready.data.local.appconst.AppConst;
import com.w3engineers.core.snacksready.data.remote.remoteconst.RemoteConst;
import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteResponse;
import com.w3engineers.core.snacksready.ui.base.BaseFragment;
import com.w3engineers.core.snacksready.databinding.FragmentAdminBinding;
import com.w3engineers.core.util.helper.DialogUtil;
import com.w3engineers.core.util.helper.Toaster;
import com.w3engineers.core.util.lib.network.callback.AdminValidatorCallBack;
import com.w3engineers.core.util.lib.network.NetworkService;

public class AdminFragment extends BaseFragment<AdminMvpView, AdminPresenter> implements AdminMvpView,
        View.OnClickListener, AdminValidatorCallBack {
    private static final String ARG_TITLE = "title";
    private String title;

    private FragmentAdminBinding fragmentAdminBinding;

    private AlertDialog loadingDialog;

    public AdminFragment() {
        // Required empty public constructor
    }

    public static AdminFragment newInstance(String param1) {
        AdminFragment fragment = new AdminFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_admin;
    }

    @Override
    protected void startUI() {
        if (getArguments() != null) title = getArguments().getString(ARG_TITLE);
        fragmentAdminBinding = (FragmentAdminBinding) getViewDataBinding();

        setClickListener(fragmentAdminBinding.btnValidator);
    }

    @Override
    public void onResume() {
        super.onResume();
        NetworkService.setAdminValidatorCallBack(this);
    }

    @Override
    protected void stopUI() {
        NetworkService.removeAdminValidatorCallBack();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_validator:
                String pin = fragmentAdminBinding.etAdminPin.getText().toString();

                if(TextUtils.isEmpty(pin)) Toaster.show("Please insert pin");
                else {
                    loadingDialog = DialogUtil.loadingDialogBuilder(getContext(), "Checking...");
                    presenter.checkAdminValidity(pin);
                }
                break;
        }
    }

    @Override
    protected AdminPresenter initPresenter() {
        return new AdminPresenter();
    }

    @Override
    public void onResponse(RemoteResponse remoteResponse) {
        if(loadingDialog != null) loadingDialog.dismiss();
        if(remoteResponse.getSuccess() == AppConst.SUCCESS)
            showAdminPanel();
        else
            Toaster.show(remoteResponse.getMessage());
    }

    @Override
    public void onFailure(String message) {
        if(loadingDialog != null) loadingDialog.dismiss();
        Toaster.show(message);
    }

    private void showAdminPanel(){
        FinestWebView.Builder webBuilder = new FinestWebView.Builder(getActivity());
        webBuilder.statusBarColorRes(R.color.colorPrimaryDark);
        webBuilder.toolbarColorRes(R.color.colorPrimary);
        webBuilder.titleDefaultRes(R.string.drawer_item_admin);
        webBuilder.showIconBack(true);
        webBuilder.showIconForward(true);
        webBuilder.showSwipeRefreshLayout(true);
        webBuilder.showProgressBar(true);
        webBuilder.setCustomAnimations(R.anim.activity_open_enter, R.anim.activity_open_exit, R.anim.activity_close_enter, R.anim.activity_close_exit);
        webBuilder.show(RemoteConst.ADMIN_BASE_URL_LOCAL);
    }
}
