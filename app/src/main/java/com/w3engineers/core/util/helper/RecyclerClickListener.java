package com.w3engineers.core.util.helper;

import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/*
* ****************************************************************************
* * Copyright © 2018 W3 Engineers Ltd., All rights reserved.
* *
* * Created by:
* * Name : SUDIPTA KUMAR PAIK
* * Date : 2/1/18
* * Email : sudipta@w3engineers.com
* *
* * Purpose : RecyclerClickListener for all type of RecyclerView OnItemTouchListener implementations
* *
* * Last Edited by : SUDIPTA KUMAR PAIK on 2/1/18.
* * History:
* * 1:
* * 2:
* *
* * Last Reviewed by : SUDIPTA KUMAR PAIK on 2/1/18.
* ****************************************************************************
*/

public class RecyclerClickListener implements RecyclerView.OnItemTouchListener {

    private OnItemChildClickListener onItemChildClickListener;

    public interface OnItemChildClickListener {
        void onChildItemClick(View view, int position);

        void onChildItemLongClick(View view, int position);
    }

    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        this.onItemChildClickListener = onItemChildClickListener;
    }

    public void onItemChildClick(View view) {
        onItemChildClickListener.onChildItemClick(view, (Integer) view.getTag());
    }

    public void onItemChildLongClick(View view) {
        onItemChildClickListener.onChildItemLongClick(view, (Integer) view.getTag());
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mListener;
    private GestureDetector mGestureDetector;
    private RecyclerView recyclerView;

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public RecyclerClickListener(final RecyclerView recyclerView, OnItemClickListener listener) {
        mListener = listener;
        this.recyclerView = recyclerView;

        mGestureDetector = new GestureDetector(recyclerView.getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());

                if (childView != null && mListener != null) {
                    mListener.onItemLongClick(childView, recyclerView.getChildAdapterPosition(childView));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View childView = rv.findChildViewUnder(e.getX(), e.getY());

        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, rv.getChildAdapterPosition(childView));
        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
