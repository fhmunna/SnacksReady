package com.w3engineers.core.snacksready.ui.lunch;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.w3engineers.core.snacksready.R;
import com.w3engineers.core.snacksready.data.local.appconst.AppConst;
import com.w3engineers.core.snacksready.data.local.lunch.Lunch;
import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteLunchList;
import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteResponse;
import com.w3engineers.core.snacksready.databinding.FragmentLunchBinding;
import com.w3engineers.core.snacksready.ui.base.BaseFragment;
import com.w3engineers.core.snacksready.ui.base.ItemClickListener;
import com.w3engineers.core.util.helper.DialogUtil;
import com.w3engineers.core.util.helper.Toaster;
import com.w3engineers.core.util.helper.ViewUtils;
import com.w3engineers.core.util.lib.network.callback.LunchCallBack;
import com.w3engineers.core.util.lib.network.NetworkService;

import java.util.ArrayList;
import java.util.List;


public class LunchFragment extends BaseFragment<LunchMvpView, LunchPresenter> implements LunchMvpView,
        ItemClickListener<Lunch>, LunchCallBack, LunchAdapter.OnLunchSelected, Animation.AnimationListener,
        DialogUtil.DialogButtonListener{
    private String title;

    private FragmentLunchBinding fragmentLunchBinding;

    private Animation show,hide;

    private AlertDialog loadingDialog, orderDialog;

    private List<Lunch> orderedItems = new ArrayList<>();
    private int numOfItems;

    public LunchFragment() {
        // Required empty public constructor
    }

    public static LunchFragment newInstance(String title) {
        LunchFragment fragment = new LunchFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_lunch;
    }

    @Override
    protected void startUI() {
        if (getArguments() != null) title = getArguments().getString("title");
        fragmentLunchBinding = (FragmentLunchBinding) getViewDataBinding();

        initView();

        setClickListener(fragmentLunchBinding.fabConfirm);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getLunchAdapter()!=null) getLunchAdapter().clear();

        NetworkService.setLunchCallBack(this);
        loadingDialog = DialogUtil.loadingDialogBuilder(getContext(), "Loading...");
        presenter.whatToLoad();
    }

    @Override
    protected void stopUI() {
        NetworkService.removeLunchCallBack();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if(view == fragmentLunchBinding.fabConfirm){
            if(orderedItems.size() > 0 && orderedItems.size() == numOfItems){
                DialogUtil.showConfirmationDialog(getContext(),
                        "Lunch confirmation", "Confirm?",
                        "Confirm now", "Choose again", this, DialogUtil.FLAG_CONFIRM);
            }
            else
                Toaster.show("Please select everyday's menu." + orderedItems.size());
        }
    }

    @Override
    protected LunchPresenter initPresenter() {
        return new LunchPresenter();
    }

    @Override
    public void onLunchListLoaded(List<Lunch> lunchList) {
        getActivity().runOnUiThread(()->{
            makeInvisibleAll();
            if(lunchList == null || lunchList.size() == 0) {
                fragmentLunchBinding.tvNoLunch.setVisibility(View.VISIBLE);
                fragmentLunchBinding.tvNoLunch.setText("No snacks yet. Keep patient.");
            }
            else {
                numOfItems = lunchList.size();
                fragmentLunchBinding.rvLunch.setVisibility(View.VISIBLE);
                LunchAdapter lunchAdapter = getLunchAdapter();

                if (lunchAdapter == null) return;

                lunchAdapter.addItems(lunchList);
            }
        });
    }

    @Override
    public void onOrderLoaded(Lunch lunch, String orderedBy) {
        getActivity().runOnUiThread(()->{
            makeInvisibleAll();
            fragmentLunchBinding.txtFixedMenu.setText("Fixed: " + lunch.getFixedMenu());
            fragmentLunchBinding.txtAlternateMenu.setText("Alternate: " + lunch.getAlternateMenu());
        });
    }

    @Override
    public void onLunchNotFound(String message) {
        getActivity().runOnUiThread(()->{
            makeInvisibleAll();
            fragmentLunchBinding.tvNoLunch.setVisibility(View.VISIBLE);
            fragmentLunchBinding.tvNoLunch.setText(message);
            Toaster.show(message);
        });
    }

    @Override
    public void onLunchConfirmed(String message, boolean success) {
        getActivity().runOnUiThread(()->{
            DialogUtil.messageDialogBuilder(getContext(), 0, message);
        });
    }

    @Override
    public void onItemClick(View view, Lunch item) {
        getActivity().runOnUiThread(()->{

        });
    }

    @Override
    public void onResponse(RemoteLunchList remoteLunchList) {
        if(loadingDialog != null) loadingDialog.dismiss();

        if(remoteLunchList.getSuccess() == AppConst.SUCCESS){
            presenter.handleRemoteLunchResponse(remoteLunchList);
        }
        else
            Toaster.show(remoteLunchList.getMessage());
    }

    @Override
    public void onConfirmLunch(RemoteResponse remoteResponse) {
        if(orderDialog != null) orderDialog.dismiss();

        presenter.handleRemoteLunchOrderConfirmationResponse(remoteResponse);
    }

    @Override
    public void onLoadLunch(Lunch lunch) {
        if(loadingDialog != null) loadingDialog.dismiss();

    }

    @Override
    public void onFailure(String message) {
        if(loadingDialog != null) loadingDialog.dismiss();
        Toaster.show(message);
    }

    @Override
    public void onSelected(int position, Lunch selectedLunch, String selectedMenu) {
        Lunch l = new Lunch(selectedLunch.getDate(), selectedLunch.getTitle(),
                selectedLunch.getFixedMenu(), selectedMenu);
        l.setId(selectedLunch.getId());
        int count = 0;
        boolean exists = false;
        for(Lunch lunch : orderedItems){
            if(lunch.getId() == selectedLunch.getId()){
                orderedItems.remove(count);
                orderedItems.add(l);
                exists = true;
                break;
            }
            count++;
        }
        if(!exists) orderedItems.add(l);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        fragmentLunchBinding.fabConfirm.clearAnimation();
        if(animation == hide)
            fragmentLunchBinding.fabConfirm.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    private void initView() {
        LunchAdapter lunchAdapter = new LunchAdapter();
        lunchAdapter.setListener(this);
        lunchAdapter.setItemClickListener(this);
        RecyclerView recyclerView = getLunchRecyclerView();
        if (recyclerView == null) return;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(ViewUtils.newLinearLayoutManager(getActivity()));
        recyclerView.setAdapter(lunchAdapter);

        show = AnimationUtils.loadAnimation(getContext(), R.anim.right_in);
        hide = AnimationUtils.loadAnimation(getContext(), R.anim.right_out);
        show.setAnimationListener(this);
        hide.setAnimationListener(this);
    }

    private RecyclerView getLunchRecyclerView() {
        return ViewUtils.getRecyclerView(getActivity(), R.id.rv_lunch);
    }

    private LunchAdapter getLunchAdapter() {
        RecyclerView.Adapter adapter = ViewUtils.getAdapter(getLunchRecyclerView());
        if (adapter != null) {
            return (LunchAdapter) adapter;
        }
        return null;
    }

    @Override
    public void onClickPositive(int flag) {
        if(flag == DialogUtil.FLAG_CONFIRM) {
            orderDialog = DialogUtil.loadingDialogBuilder(getContext(), "Confirming...");
            presenter.confirmLunch(orderedItems);
        }
    }

    @Override
    public void onCancel(int flag) {

    }

    @Override
    public void onClickNegative(int flag) {

    }

    private void makeInvisibleAll(){
        fragmentLunchBinding.tvNoLunch.setVisibility(View.INVISIBLE);
        fragmentLunchBinding.rvLunch.setVisibility(View.INVISIBLE);
        fragmentLunchBinding.orderedItem.setVisibility(View.INVISIBLE);
    }
}
