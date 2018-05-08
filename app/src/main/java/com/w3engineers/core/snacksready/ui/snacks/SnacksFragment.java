package com.w3engineers.core.snacksready.ui.snacks;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.w3engineers.core.snacksready.R;
import com.w3engineers.core.snacksready.data.local.snack.Snack;
import com.w3engineers.core.snacksready.data.remote.remoteconst.RemoteConst;
import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteOrder;
import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteResponse;
import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteSnacks;
import com.w3engineers.core.snacksready.databinding.FragmentSnacksBinding;
import com.w3engineers.core.snacksready.ui.base.BaseFragment;
import com.w3engineers.core.snacksready.ui.base.ItemClickListener;
import com.w3engineers.core.util.helper.DialogUtil;
import com.w3engineers.core.util.helper.Glider;
import com.w3engineers.core.util.helper.Toaster;
import com.w3engineers.core.util.helper.ViewUtils;
import com.w3engineers.core.util.lib.network.NetworkService;
import com.w3engineers.core.util.lib.network.SnacksCallBack;

import java.util.List;


public class SnacksFragment extends BaseFragment<SnacksMvpView, SnacksPresenter> implements SnacksMvpView,
        ItemClickListener<Snack>, Animation.AnimationListener, SnacksCallBack, DialogUtil.DialogButtonListener{
    private String title;

    private FragmentSnacksBinding fragmentSnacksBinding;

    private Animation show,hide;

    private AlertDialog loadingDialog, orderDialog;

    private Snack selectedSnack;

    public SnacksFragment() {
        // Required empty public constructor
    }

    public static SnacksFragment newInstance(String title) {
        SnacksFragment fragment = new SnacksFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_snacks;
    }

    @Override
    protected int getMenuId() {
        return R.menu.menu_snacks;
    }

    @Override
    protected void startUI() {
        if (getArguments() != null) title = getArguments().getString("title");
        fragmentSnacksBinding = (FragmentSnacksBinding) getViewDataBinding();

        initView();

        setClickListener(fragmentSnacksBinding.fabConfirm);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getSnacksAdapter()!=null) getSnacksAdapter().clear();

        NetworkService.setSnacksCallBack(this);
        loadingDialog = DialogUtil.loadingDialogBuilder(getContext(), "Loading...");
        presenter.whatToLoad();
    }

    @Override
    protected void stopUI() {
        NetworkService.removeSnacksCallBack();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if(view == fragmentSnacksBinding.fabConfirm){
            if(selectedSnack != null)
                DialogUtil.showConfirmationDialog(getContext(),
                        "Snacks confirmation", "Confirm " + selectedSnack.getTitle(),
                        "Confirm now", "Choose again", this, DialogUtil.FLAG_CONFIRM);
        }
    }

    @Override
    protected SnacksPresenter initPresenter() {
        return new SnacksPresenter();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_all_snacks:
                if(item.getTitle().toString().equalsIgnoreCase(getString(R.string.all_snacks))) {
                    item.setTitle(getString(R.string.today_snacks));
                    presenter.loadSnacks();
                }
                else {
                    item.setTitle(getString(R.string.all_snacks));
                    presenter.whatToLoad();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSnacksLoaded(List<Snack> snacks) {
        getActivity().runOnUiThread(()->{
            makeInvisibleAll();
            if(snacks == null || snacks.size() == 0) {
                fragmentSnacksBinding.tvNoSnacks.setVisibility(View.VISIBLE);
                fragmentSnacksBinding.tvNoSnacks.setText("No snacks yet. Keep patient.");
            }
            else {
                fragmentSnacksBinding.rvSnacks.setVisibility(View.VISIBLE);
                SnacksAdapter snacksAdapter = getSnacksAdapter();

                if (snacksAdapter == null) return;

                snacksAdapter.addItems(snacks);
            }
        });
    }

    @Override
    public void onOrderLoaded(Snack snack, String orderedBy) {
        getActivity().runOnUiThread(()->{
            makeInvisibleAll();
            Glider.show(RemoteConst.BASE_IMAGE_PATH + snack.getImage(), fragmentSnacksBinding.imgSnack);
            fragmentSnacksBinding.txtTitle.setText(snack.getTitle());
            fragmentSnacksBinding.txtReviewCount.setText(snack.getReviewCount() + " reviews");
            float rating = 0;
            if(snack.getReviewCount() > 0) rating = snack.getRatingSum()/snack.getReviewCount();
            fragmentSnacksBinding.ratingBar.setRating(rating);
            fragmentSnacksBinding.txtOrderCount.setText("Total " + snack.getOrderCount() + " orders");
            fragmentSnacksBinding.txtOrderedBy.setText("Ordered from IP: " + orderedBy);
            fragmentSnacksBinding.orderedItem.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void onSnacksNotFound(String message) {
        getActivity().runOnUiThread(()->{
            makeInvisibleAll();
            fragmentSnacksBinding.tvNoSnacks.setVisibility(View.VISIBLE);
            fragmentSnacksBinding.tvNoSnacks.setText(message);
            Toaster.show(message);
        });
    }

    @Override
    public void onSnackConfirmed(String message, boolean success) {
        getActivity().runOnUiThread(()->{
            DialogUtil.messageDialogBuilder(getContext(), 0, message);
        });
    }

    @Override
    public void onItemClick(View view, Snack item) {
        getActivity().runOnUiThread(()->{
            if(getSnacksAdapter() != null) {
                fragmentSnacksBinding.fabConfirm.clearAnimation();
                if(getSnacksAdapter().isAnySelection()) {
                    selectedSnack = item;
                    if(fragmentSnacksBinding.fabConfirm.getVisibility() == View.INVISIBLE) {
                        fragmentSnacksBinding.fabConfirm.setVisibility(View.VISIBLE);
                        fragmentSnacksBinding.fabConfirm.startAnimation(show);
                    }
                }
                else {
                    selectedSnack = null;
                    if(fragmentSnacksBinding.fabConfirm.getVisibility() == View.VISIBLE)
                        fragmentSnacksBinding.fabConfirm.startAnimation(hide);
                }
            }
        });
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        fragmentSnacksBinding.fabConfirm.clearAnimation();
        if(animation == hide)
            fragmentSnacksBinding.fabConfirm.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onResponse(RemoteSnacks remoteSnacks) {
        if(loadingDialog != null) loadingDialog.dismiss();
        Toaster.show(remoteSnacks.getMessage());

        presenter.handleResponse(remoteSnacks);
    }

    @Override
    public void onPlaceOrder(RemoteResponse remoteResponse) {
        getActivity().runOnUiThread(()->{
            if(orderDialog != null) orderDialog.dismiss();

            presenter.handleOrderPlacementResponse(remoteResponse);
        });
    }

    @Override
    public void onLoadOrder(RemoteOrder remoteOrder) {
        getActivity().runOnUiThread(()->{
            if(loadingDialog != null) loadingDialog.dismiss();

            presenter.handleLoadedOrder(remoteOrder);
        });
    }

    @Override
    public void onFailure(String message) {
        getActivity().runOnUiThread(()->{
            if(loadingDialog != null) loadingDialog.dismiss();
            Toaster.show(message);
        });
    }

    private void initView() {
        SnacksAdapter snacksAdapter = new SnacksAdapter();
        snacksAdapter.setItemClickListener(this);
        RecyclerView recyclerView = getSnacksRecyclerView();
        if (recyclerView == null) return;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(ViewUtils.newLinearLayoutManager(getActivity()));
        recyclerView.setAdapter(snacksAdapter);

        show = AnimationUtils.loadAnimation(getContext(), R.anim.right_in);
        hide = AnimationUtils.loadAnimation(getContext(), R.anim.right_out);
        show.setAnimationListener(this);
        hide.setAnimationListener(this);
    }

    private RecyclerView getSnacksRecyclerView() {
        return ViewUtils.getRecyclerView(getActivity(), R.id.rv_snacks);
    }

    private SnacksAdapter getSnacksAdapter() {
        RecyclerView.Adapter adapter = ViewUtils.getAdapter(getSnacksRecyclerView());
        if (adapter != null) {
            return (SnacksAdapter) adapter;
        }
        return null;
    }

    @Override
    public void onClickPositive(int flag) {
        if(flag == DialogUtil.FLAG_CONFIRM) {
            orderDialog = DialogUtil.loadingDialogBuilder(getContext(), "Confirming order ...");
            presenter.confirmSnack(selectedSnack);
        }
    }

    @Override
    public void onCancel(int flag) {

    }

    @Override
    public void onClickNegative(int flag) {

    }


    private void makeInvisibleAll(){
        fragmentSnacksBinding.tvNoSnacks.setVisibility(View.INVISIBLE);
        fragmentSnacksBinding.rvSnacks.setVisibility(View.INVISIBLE);
        fragmentSnacksBinding.orderedItem.setVisibility(View.INVISIBLE);
        fragmentSnacksBinding.fabConfirm.setVisibility(View.INVISIBLE);
    }
}
