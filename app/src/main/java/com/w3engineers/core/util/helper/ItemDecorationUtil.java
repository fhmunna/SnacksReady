package com.w3engineers.core.util.helper;
/*
 *  ****************************************************************************
 *  * Created by : Md. Hasnain on 4/24/2018 at 3:21 PM.
 *  * Email : ashik.pstu.cse@gmail.com
 *  *
 *  * Last edited by : Md. Hasnain on 4/24/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ItemDecorationUtil extends RecyclerView.ItemDecoration {
    private int space;

    public ItemDecorationUtil(int space) {
        this.space = space;
    }

    int halfSpace = space / 2;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (parent.getPaddingLeft() != halfSpace) {
            parent.setPadding(halfSpace, halfSpace, halfSpace, halfSpace);
            parent.setClipToPadding(false);
        }
        outRect.top = halfSpace;
        outRect.bottom = halfSpace;
        outRect.left = halfSpace;
        outRect.right = halfSpace;
    }
}
