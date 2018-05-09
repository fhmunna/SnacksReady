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

    @Override
    public void onBindViewHolder(BaseViewHolder<Lunch> holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    private class LunchViewHolder extends BaseViewHolder<Lunch>{
        ItemLunchBinding mItemLunchBinding;

        LunchViewHolder(ItemLunchBinding itemLunchBinding) {
            super(itemLunchBinding.getRoot());
            this.mItemLunchBinding = itemLunchBinding;

            mItemLunchBinding.rgMenu.setOnCheckedChangeListener((group, checkedId) -> {
                RadioButton radioButton = group.findViewById(checkedId);
                if(radioButton != null){
                    String selectedAlterMenu = radioButton.getText().toString();
                    getItem(getAdapterPosition()).setSelectedAlterMenu(selectedAlterMenu);
                    if(mListener != null) mListener.onSelected(getAdapterPosition(),
                            getItem(getAdapterPosition()), selectedAlterMenu);
                }
            });
        }

        @Override
        public void bind(Lunch item) {
            mItemLunchBinding.txtDate.setText(item.getDate());
            mItemLunchBinding.txtFixedMenu.setText("Fixed: " + item.getFixedMenu());

            if(!item.getAlternateMenu().equals("")){
                String[] alterMenu = item.getAlternateMenu().split(",");
                mItemLunchBinding.rgMenu.removeAllViews();
                for (String str : alterMenu) {
                    boolean checked = false;
                    String selectedAlterMenu = item.getSelectedAlterMenu();
                    if(selectedAlterMenu != null && selectedAlterMenu.equalsIgnoreCase(str))
                        checked = true;
                    mItemLunchBinding.rgMenu.addView(createRadioButton(str, checked));
                }
            }
        }

        @Override
        public void onClick(View view) {
        }
    }

    private RadioButton createRadioButton(String text, boolean isChecked){
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
        rb.setChecked(isChecked);
        CompoundButtonCompat.setButtonTintList(rb, colorStateList);

        return rb;
    }

    public interface OnLunchSelected{
        void onSelected(int position, Lunch selectedLunch, String selectedMenu);
    }
}