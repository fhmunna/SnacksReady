package com.w3engineers.core.snacksready.ui.lunch;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.w3engineers.core.snacksready.R;
import com.w3engineers.core.snacksready.data.local.lunch.Lunch;
import com.w3engineers.core.snacksready.data.remote.remoteconst.RemoteConst;
import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteOrder;
import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteResponse;
import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteSnacks;
import com.w3engineers.core.snacksready.databinding.FragmentLunchBinding;
import com.w3engineers.core.snacksready.ui.base.BaseFragment;
import com.w3engineers.core.snacksready.ui.base.ItemClickListener;
import com.w3engineers.core.util.helper.DialogUtil;
import com.w3engineers.core.util.helper.Glider;
import com.w3engineers.core.util.helper.Toaster;
import com.w3engineers.core.util.helper.ViewUtils;
import com.w3engineers.core.util.lib.network.NetworkService;

import java.util.List;


public class LunchFragment extends BaseFragment<LunchMvpView, LunchPresenter> implements LunchMvpView,
        ItemClickListener<Lunch>, Animation.AnimationListener, DialogUtil.DialogButtonListener{
    private String title;

    private FragmentLunchBinding fragmentLunchBinding;

    private Animation show,hide;

    private AlertDialog loadingDialog, orderDialog;

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

        //loadingDialog = DialogUtil.loadingDialogBuilder(getContext(), "Loading...");
        presenter.whatToLoad();
    }

    @Override
    protected void stopUI() {
        NetworkService.removeSnacksCallBack();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if(view == fragmentLunchBinding.fabConfirm){
            DialogUtil.showConfirmationDialog(getContext(),
                    "Lunch confirmation", "Confirm?",
                    "Confirm now", "Choose again", this, DialogUtil.FLAG_CONFIRM);
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
            Glider.show(RemoteConst.BASE_IMAGE_PATH + lunch.getImage(), fragmentLunchBinding.imgLunch);
            fragmentLunchBinding.txtFixedMenu.setText("Fixed: " + lunch.getFixedMenu());
            fragmentLunchBinding.txtAlternateMenu.setText("Alternate: " + lunch.getAlternateMenu()[0]);
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
            orderDialog = DialogUtil.loadingDialogBuilder(getContext(), "Confirming order ...");
            presenter.confirmLunch();
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
