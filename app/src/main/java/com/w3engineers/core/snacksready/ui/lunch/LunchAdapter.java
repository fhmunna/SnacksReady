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

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.w3engineers.core.AppController;
import com.w3engineers.core.snacksready.R;
import com.w3engineers.core.snacksready.data.local.lunch.Lunch;
import com.w3engineers.core.snacksready.data.remote.remoteconst.RemoteConst;
import com.w3engineers.core.snacksready.databinding.ItemLunchBinding;
import com.w3engineers.core.snacksready.ui.base.BaseAdapter;
import com.w3engineers.core.snacksready.ui.base.BaseViewHolder;
import com.w3engineers.core.util.helper.Glider;
import com.w3engineers.core.util.helper.Toaster;

import java.util.ArrayList;
import java.util.List;

public class LunchAdapter extends BaseAdapter<Lunch> {
    private static OnLunchSelected mListener;

    public void setListener(OnLunchSelected listener){
        mListener = listener;
    }

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

            if(!item.getAlternateMenu().equals("")){
                String[] alterMenu = item.getAlternateMenu().split(",");
                if(mItemLunchBinding.rgMenu.getChildCount() == 0){
                    for (String str: alterMenu) {
                        if(!str.equals("")) mItemLunchBinding.rgMenu.addView(createRadioButton(str));
                    }
                }
            }

            mItemLunchBinding.rgMenu.setOnCheckedChangeListener((group, checkedId) -> {
                RadioButton radioButton = group.findViewById(checkedId);
                Lunch lunch = getItem(getAdapterPosition());
                lunch.setAlternateMenu(radioButton.getText().toString());
                if(mListener != null) mListener.onSelected(getAdapterPosition(), lunch);
            });
        }

        @Override
        public void onClick(View view) {
            notifyDataSetChanged();
        }
    }

    private RadioButton createRadioButton(String text){
        RadioButton rb;
        ColorStateList colorStateList = new ColorStateList(
            new int[][]{
                    new int[]{-android.R.attr.state_checked},
                    new int[]{android.R.attr.state_checked}
            },
            new int[]{
                    Color.WHITE
                    , Color.rgb (242,81,112),
            }
        );
        rb = new RadioButton(AppController.getContext());
        rb.setText(text);
        rb.setTextColor(Color.WHITE);
        rb.setChecked(false);
        CompoundButtonCompat.setButtonTintList(rb, colorStateList);

        return rb;
    }

    public interface OnLunchSelected{
        void onSelected(int position, Lunch selectedLunch);
    }
}