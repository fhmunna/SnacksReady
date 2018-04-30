package com.w3engineers.core.snacksready.ui.lunch;
/*
 *  ****************************************************************************
 *  * Created by : Md. Hasnain on 4/24/2018 at 1:58 PM.
 *  * Email : ashik.pstu.cse@gmail.com
 *  *
 *  * Last edited by : Md. Hasnain on 4/24/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.w3engineers.core.AppController;
import com.w3engineers.core.snacksready.R;
import com.w3engineers.core.snacksready.data.local.snack.Snack;
import com.w3engineers.core.snacksready.data.remote.remoteconst.RemoteConst;
import com.w3engineers.core.snacksready.databinding.ItemSnackBinding;
import com.w3engineers.core.snacksready.ui.base.BaseAdapter;
import com.w3engineers.core.snacksready.ui.base.BaseViewHolder;
import com.w3engineers.core.util.helper.Glider;
import com.w3engineers.core.util.helper.MyBounceInterPolator;
import com.w3engineers.core.util.helper.NumberUtil;

public class LunchAdapter extends BaseAdapter<Snack> {
    private int selectedIndex = -1;
    private int prevSelectedIndex = -1;
    private boolean isAnySelection = false;
    private Animation anim1, anim2;

    @Override
    public boolean isEqual(Snack left, Snack right) {
        return left.getId() == right.getId();
    }

    public boolean isAnySelection() {
        return isAnySelection;
    }

    @Override
    public BaseViewHolder newViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemSnackBinding itemSnackBinding = ItemSnackBinding.inflate(layoutInflater, parent, false);

        return new SnacksViewHolder(itemSnackBinding);
    }

    private class SnacksViewHolder extends BaseViewHolder<Snack>{
        ItemSnackBinding mItemSnackBinding;

        SnacksViewHolder(ItemSnackBinding itemSnackBinding) {
            super(itemSnackBinding.getRoot());
            this.mItemSnackBinding = itemSnackBinding;

            setClickListener(mItemSnackBinding.itemContainer);
        }

        @Override
        public void bind(Snack item) {
            initAnim();
            mItemSnackBinding.txtTitle.setText(item.getTitle());
            mItemSnackBinding.txtOrderCount.setText(item.getOrderCount() + " orders already");
            mItemSnackBinding.txtRating.setText(item.getReviewCount() + " reviews") ;
            float rating = item.getRatingSum()/(float)item.getReviewCount();
            String ratingStr = "*" + NumberUtil.formatFloatNumber(rating, 2, 2);
            mItemSnackBinding.imgSnack.setLabelText(ratingStr);
            mItemSnackBinding.ratingBar.setRating(rating);

            if (item.isOrdered()) {
                mItemSnackBinding.parentContainer.startAnimation(anim1);
                mItemSnackBinding.itemContainer.startAnimation(anim2);

                mItemSnackBinding.itemSelected.setVisibility(View.VISIBLE);
                mItemSnackBinding.animationView.setImageAssetsFolder("raw/");
                mItemSnackBinding.animationView.setAnimation(R.raw.finish_done);
                mItemSnackBinding.animationView.playAnimation();
            }
            else mItemSnackBinding.itemSelected.setVisibility(View.INVISIBLE);

            Glider.show(RemoteConst.BASE_IMAGE_PATH + item.getImage(), mItemSnackBinding.imgSnack);
        }

        @Override
        public void onClick(View view) {
            prevSelectedIndex = selectedIndex;
            selectedIndex = getAdapterPosition();
            getItem(selectedIndex).setOrdered(true);
            if(prevSelectedIndex != -1) getItem(prevSelectedIndex).setOrdered(false);
            if(selectedIndex == prevSelectedIndex) {
                isAnySelection = false;
                selectedIndex = -1;
            }
            else isAnySelection = true;

            mItemClickListener.onItemClick(view, getItem(selectedIndex));

            notifyDataSetChanged();
        }
    }

    private void initAnim(){
        if(anim1 == null) {
            anim1 =  AnimationUtils.loadAnimation(AppController.getContext(), R.anim.bounce);
            MyBounceInterPolator interpolator = new MyBounceInterPolator(0.07, 20);
            anim1.setInterpolator(interpolator);
        }
        if(anim2 == null) {
            anim2 =  AnimationUtils.loadAnimation(AppController.getContext(), R.anim.bounce);
            MyBounceInterPolator interpolator = new MyBounceInterPolator(0.05, 15);
            anim2.setInterpolator(interpolator);
        }
    }
}
