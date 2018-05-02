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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.w3engineers.core.AppController;
import com.w3engineers.core.snacksready.R;
import com.w3engineers.core.snacksready.data.local.lunch.Lunch;
import com.w3engineers.core.snacksready.data.local.snack.Snack;
import com.w3engineers.core.snacksready.data.remote.remoteconst.RemoteConst;
import com.w3engineers.core.snacksready.databinding.ItemLunchBinding;
import com.w3engineers.core.snacksready.ui.base.BaseAdapter;
import com.w3engineers.core.snacksready.ui.base.BaseViewHolder;
import com.w3engineers.core.util.helper.Glider;
import com.w3engineers.core.util.helper.MyBounceInterPolator;
import com.w3engineers.core.util.helper.NumberUtil;
import com.w3engineers.core.util.helper.Toaster;

public class LunchAdapter extends BaseAdapter<Lunch> {

    @Override
    public boolean isEqual(Lunch left, Lunch right) {
        return left.getId() == right.getId();
    }

    @Override
    public BaseViewHolder newViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemLunchBinding itemLunchBinding = ItemLunchBinding.inflate(layoutInflater, parent, false);

        return new LunchViewHolder(itemLunchBinding);
    }

    private class LunchViewHolder extends BaseViewHolder<Lunch>{
        ItemLunchBinding mItemLunchBinding;

        LunchViewHolder(ItemLunchBinding itemLunchBinding) {
            super(itemLunchBinding.getRoot());
            this.mItemLunchBinding = itemLunchBinding;

            setClickListener(mItemLunchBinding.itemContainer);
        }

        @Override
        public void bind(Lunch item) {
            mItemLunchBinding.txtDate.setText(item.getDate());
            mItemLunchBinding.txtFixedMenu.setText("Fixed: " + item.getFixedMenu());

            mItemLunchBinding.rgMenu.setOnCheckedChangeListener((group, checkedId) -> {
                RadioButton radioButton = group.findViewById(checkedId);
                Toaster.show(radioButton.getText().toString());
            });

            Glider.show(RemoteConst.BASE_IMAGE_PATH + item.getImage(), mItemLunchBinding.imgLunch);
        }

        @Override
        public void onClick(View view) {
            notifyDataSetChanged();
        }
    }
}
