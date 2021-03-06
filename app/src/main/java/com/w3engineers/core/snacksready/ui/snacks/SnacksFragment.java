package com.w3engineers.core.snacksready.ui.snacks;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.w3engineers.core.snacksready.R;
import com.w3engineers.core.snacksready.data.local.snack.Snack;
import com.w3engineers.core.snacksready.databinding.FragmentSnacksBinding;
import com.w3engineers.core.snacksready.ui.base.BaseFragment;
import com.w3engineers.core.snacksready.ui.base.ItemClickListener;
import com.w3engineers.core.util.helper.ItemDecorationUtil;
import com.w3engineers.core.util.helper.Toaster;
import com.w3engineers.core.util.helper.ViewUtils;

import java.util.List;


public class SnacksFragment extends BaseFragment<SnacksMvpView, SnacksPresenter> implements SnacksMvpView,
        ItemClickListener<Snack>, Animation.AnimationListener{
    private String title;

    private FragmentSnacksBinding fragmentSnacksBinding;

    Animation show,hide;

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
    protected void startUI() {
        if (getArguments() != null) title = getArguments().getString("title");
        fragmentSnacksBinding = (FragmentSnacksBinding) getViewDataBinding();

        initView();

        setClickListener(fragmentSnacksBinding.fabConfirm);

        presenter.loadSnacks();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getSnacksAdapter()!=null) getSnacksAdapter().clear();
        presenter.loadSnacks();
    }

    @Override
    protected void stopUI() {

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if(view == fragmentSnacksBinding.fabConfirm){
            Toaster.show("Confirm?");
        }
    }

    @Override
    protected SnacksPresenter initPresenter() {
        return new SnacksPresenter();
    }

    @Override
    public void onSnacksLoaded(List<Snack> snacks) {
        getActivity().runOnUiThread(()->{
            if(snacks == null || snacks.size() == 0) {
                fragmentSnacksBinding.tvNoSnacks.setVisibility(View.VISIBLE);
                fragmentSnacksBinding.tvNoSnacks.setText("No snacks yet. Keep patient.");
            }
            else {
                fragmentSnacksBinding.tvNoSnacks.setVisibility(View.INVISIBLE);
                SnacksAdapter snacksAdapter = getSnacksAdapter();

                if (snacksAdapter == null) return;

                snacksAdapter.addItems(snacks);
            }
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
    public void onItemClick(View view, Snack item) {
        getActivity().runOnUiThread(()->{
            if(getSnacksAdapter() != null) {
                fragmentSnacksBinding.fabConfirm.clearAnimation();
                if(getSnacksAdapter().isAnySelection()) {
                    fragmentSnacksBinding.fabConfirm.setVisibility(View.VISIBLE);
                    fragmentSnacksBinding.fabConfirm.startAnimation(show);
                }
                else {
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
}
