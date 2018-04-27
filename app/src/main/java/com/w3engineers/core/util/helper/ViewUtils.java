/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.w3engineers.core.util.helper;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.w3engineers.core.AppController;
import com.w3engineers.core.snacksready.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;

/*
* ****************************************************************************
* * Copyright © 2018 W3 Engineers Ltd., All rights reserved.
* *
* * Created by:
* * Name : SUDIPTA KUMAR PAIK
* * Date : 2/1/18
* * Email : sudipta@w3engineers.com
* *
* * Purpose : ViewUtils for all view related Utils method
* *
* * Last Edited by : SUDIPTA KUMAR PAIK on 2/1/18.
* * History:
* * 1:
* * 2:
* *
* * Last Reviewed by : SUDIPTA KUMAR PAIK on 2/1/18.
* ****************************************************************************
*/

public final class ViewUtils {
    private Context context;

    public static final Semaphore LOCK = new Semaphore(0);

    private ViewUtils() {
        // This utility class is not publicly instantiable
    }

    private ViewUtils(Context context) {
        this.context = context;
    }

    public static float pxToDp(float px) {
        float densityDpi = Resources.getSystem().getDisplayMetrics().densityDpi;
        return px / (densityDpi / 160f);
    }

    public static int dpToPx(float dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    public static void changeIconDrawableToGray(Context context, Drawable drawable) {
        if (drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(ContextCompat
                    .getColor(context, R.color.colorGray), PorterDuff.Mode.SRC_ATOP);
        }
    }

    public static boolean isVisible(Fragment fragment, int viewId) {
        View view = getViewById(fragment, viewId);
        if (view != null) {
            return view.getVisibility() == View.VISIBLE;
        }
        return false;
    }

    public static View getViewById(Fragment fragment, int viewId) {
        return getViewById(fragment.getView(), viewId);
    }

    public static boolean isChecked(Fragment fragment, int viewId) {
        View view = getViewById(fragment, viewId);
        if (CompoundButton.class.isInstance(view)) {
            return ((CompoundButton) view).isChecked();
        }
        return false;
    }

    public static void setVisibility(Activity activity, int viewId, int visibility) {
        setVisibility(getViewById(activity, viewId), visibility);
    }

    public static void setVisibility(Fragment fragment, int viewId, int visibility) {
        setVisibility(getViewById(fragment, viewId), visibility);
    }

    public static void setVisibility(View view, int visibility) {
        if (view != null) {
            view.setVisibility(visibility);
        }
    }

    public static void setVisibility(View parentView, int viewId, int visibility) {
        setVisibility(getViewById(parentView, viewId), visibility);
    }


    // View Related works
    public static boolean isNull(View view) {
        return view == null;
    }

    public static boolean isNull(Activity activity) {
        return activity == null;
    }

    public static boolean isNull(Fragment fragment) {
        return fragment == null;
    }

    private static boolean isNull(Object view) {
        return view == null;
    }


    public static Spinner getSpinnerById(Activity activity, int viewId) {
        if (!isNull(activity) && viewId > 0) {
            return (Spinner) activity.findViewById(viewId);
        }
        return null;
    }

    public static Spinner getSpinnerById(Fragment fragment, int viewId) {
        if (!isNull(fragment) && viewId > 0) {
            return (Spinner) fragment.getView().findViewById(viewId);
        }
        return null;
    }

    public static LinearLayout getLinearLayoutById(Activity activity, int viewId) {
        if (!isNull(activity) && viewId > 0) {
            return (LinearLayout) activity.findViewById(viewId);
        }
        return null;
    }

    public static LinearLayout getLinearLayoutById(Fragment fragment, int viewId) {
        if (!isNull(fragment) && viewId > 0) {
            return (LinearLayout) fragment.getView().findViewById(viewId);
        }
        return null;
    }

    public static TextView getTextViewById(Activity activity, int viewId) {
        if (!isNull(activity) && viewId > 0) {
            return (TextView) activity.findViewById(viewId);
        }
        return null;
    }

    public static TextView getTextViewById(Fragment fragment, int viewId) {
        if (!isNull(fragment) && viewId > 0) {
            return (TextView) fragment.getView().findViewById(viewId);
        }
        return null;
    }


    public static EditText getEditTextById(Activity activity, int viewId) {
        if (!isNull(activity) && viewId > 0) {
            return (EditText) activity.findViewById(viewId);
        }
        return null;
    }

    public static EditText getEditTextById(Fragment fragment, int viewId) {
        if (!isNull(fragment) && viewId > 0) {
            return (EditText) fragment.getView().findViewById(viewId);
        }
        return null;
    }

    public static WebView getWebViewById(Fragment fragment, int viewId) {
        if (!isNull(fragment) && viewId > 0) {
            return (WebView) fragment.getView().findViewById(viewId);
        }
        return null;
    }

    public static Button getButtonById(Fragment fragment, int viewId) {
        if (!isNull(fragment) && viewId > 0) {
            return (Button) fragment.getView().findViewById(viewId);
        }
        return null;
    }

    public static ImageButton getImageButtonById(Fragment fragment, int viewId) {
        if (!isNull(fragment) && viewId > 0) {
            return (ImageButton) fragment.getView().findViewById(viewId);
        }
        return null;
    }

    public static ImageButton getImageButtonById(Activity activity, int viewId) {
        if (!isNull(activity) && viewId > 0) {
            return (ImageButton) activity.findViewById(viewId);
        }
        return null;
    }

    public static ImageView getImageViewById(Fragment fragment, int viewId) {
        if (!isNull(fragment) && viewId > 0) {
            return (ImageView) fragment.getView().findViewById(viewId);
        }
        return null;
    }

    public static View getViewById(View parentView, int viewId) {
        if (!isNull(parentView) && viewId > 0) {
            return parentView.findViewById(viewId);
        }
        return null;
    }

    public static View getViewById(Activity activity, int viewId) {
        if (!isNull(activity) && viewId > 0) {
            return activity.findViewById(viewId);
        }
        return null;
    }

    public static ImageView getImageViewById(Activity activity, int viewId) {
        if (!isNull(activity) && viewId > 0) {
            return (ImageView) activity.findViewById(viewId);
        }
        return null;
    }


    public static Button getButtonById(Activity activity, int viewId) {
        if (!isNull(activity) && viewId > 0) {
            return (Button) activity.findViewById(viewId);
        }
        return null;
    }

    public static ToggleButton getToggleButtonById(Activity activity, int viewId) {
        if (!isNull(activity) && viewId > 0) {
            return (ToggleButton) activity.findViewById(viewId);
        }
        return null;
    }

    public static ViewPager getViewPager(View parentView, int viewPagerId) {
        View viewPager = getViewById(parentView, viewPagerId);
        if (ViewPager.class.isInstance(viewPager)) {
            return (ViewPager) viewPager;
        }
        return null;
    }

    public static TabLayout getTabLayout(View parentView, int tabLayoutId) {
        View tabLayout = getViewById(parentView, tabLayoutId);
        if (TabLayout.class.isInstance(tabLayout)) {
            return (TabLayout) tabLayout;
        }
        return null;
    }

    public static Toolbar getToolbar(Activity activity, int toolbarId) {
        View toolbar = getViewById(activity, toolbarId);
        if (Toolbar.class.isInstance(toolbar)) {
            return (Toolbar) toolbar;
        }
        return null;
    }

    public static boolean setClickListener(View view, View.OnClickListener clickListener) {
        if (!isNull(view)) {
            view.setOnClickListener(clickListener);
            return true;
        }
        return false;
    }

    public static boolean setClickListener(Activity activity, int viewId, View.OnClickListener clickListener) {
        View view = getViewById(activity, viewId);
        if (!isNull(view)) {
            view.setOnClickListener(clickListener);
            return true;
        }
        return false;
    }

    public static boolean setClickListener(Fragment fragment, int viewId, View.OnClickListener clickListener) {
        View view = getViewById(fragment.getView(), viewId);
        if (!isNull(view)) {
            view.setOnClickListener(clickListener);
            return true;
        }
        return false;
    }

    public static boolean setClickListener(View parentView, int viewId, View.OnClickListener clickListener) {
        View view = getViewById(parentView, viewId);
        if (!isNull(view)) {
            view.setOnClickListener(clickListener);
            return true;
        }
        return false;
    }


    public static boolean setCheckedListener(Fragment fragment, int viewId, CompoundButton.OnCheckedChangeListener changeListener) {
        View view = getViewById(fragment.getView(), viewId);
        if (CompoundButton.class.isInstance(view)) {
            ((CompoundButton) view).setOnCheckedChangeListener(changeListener);
            return true;
        }
        return false;
    }

    public static void setText(Activity activity, int viewId, String text) {
        setText(getViewById(activity, viewId), text);
    }

    public static void setError(Activity activity, int viewId, String text) {
        setError(getViewById(activity, viewId), text);
    }

    public static void setText(Fragment fragment, int viewId, String text) {
        setText(getViewById(fragment.getView(), viewId), text);
    }

    public static void setError(Fragment fragment, int viewId, String text) {
        setError(getViewById(fragment.getView(), viewId), text);
    }

    public static void setText(View parentView, int viewId, String text) {
        setText(getViewById(parentView, viewId), text);
    }

    public static void setText(View parentView, int viewId, int value) {
        setText(getViewById(parentView, viewId), value);
    }

    public static void setText(View view, long value) {
        setText(view, Long.toString(value));
    }

    public static void setText(View view, String text) {
        if (TextView.class.isInstance(view)) {
            ((TextView) view).setText(text);
        }
    }

    public static void setError(View view, String text) {
        if (TextView.class.isInstance(view)) {
            ((TextView) view).setError(text);
        }
    }

    public static void setTextColor(View view, int colorId) {
        if (TextView.class.isInstance(view)) {
            ((TextView) view).setTextColor(AndroidUtil.getColor(view.getContext(), colorId));
        }
    }


    public static RecyclerView.LayoutManager newLinearLayoutManager(Context context) {
        return new LinearLayoutManager(context);
    }

    public static int dpToPx(int dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
    }

    public static RecyclerView.LayoutManager newGridLayoutManager(Context context, int columns) {
        return new GridLayoutManager(context, columns);
    }


    public static RecyclerView.LayoutManager newStaggeredGridLayoutManager(int spanCount, int orientation) {
        return new StaggeredGridLayoutManager(spanCount, orientation);
    }

    public static RecyclerView.Adapter getAdapter(RecyclerView recyclerView) {
        if (recyclerView != null) {
            return recyclerView.getAdapter();
        }
        return null;
    }

    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }

        return 0;
    }

    public static int getActionBarHeight(Context context) {
        TypedValue tv = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);

        int actionBarHeight = context.getResources().getDimensionPixelSize(tv.resourceId);

        if (actionBarHeight > 0) {
            return actionBarHeight;
        }
        return 0;
    }

    public static int getHeight(Context context) {
        //DisplayMetrics displayMetrics = new DisplayMetrics();

        return context.getResources().getDisplayMetrics().heightPixels;
    }


    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

    public static void isScanMedia(String... paths) {
        MediaScannerConnection.scanFile(AppController.getContext(), paths, null,
            new MediaScannerConnection.MediaScannerConnectionClient() {

                @Override
                public void onMediaScannerConnected() {

                }

                @Override
                public void onScanCompleted(String s, Uri uri) {

                }
            });
    }

    public static boolean isEmpty(String... strings) {
        for (String string : strings) {
            if (TextUtils.isEmpty(string))
                return true;
        }
        return false;
    }

    public static String lastUpdateDate(double value) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        String lastUpdatedTime = dateFormat.format(value);
        return "Updated at " + lastUpdatedTime;
    }

    public static String geoLocation(double latitute, double longitute) {
        return latitute + ", " + longitute;
    }

    public static String getDistance(String distance) {
        return String.valueOf(new DecimalFormat("##.##").format(distance));
    }

    public static boolean isServiceRunning(Class<?> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static String getTimeAgo(long time, final Context context) {

        final int SECOND_MILLIS = 1000;
        final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        final int DAY_MILLIS = 24 * HOUR_MILLIS;
        final long WEEK_IN_MILLIS = DAY_MILLIS * 7;
        final long YEAR_MILLIS = WEEK_IN_MILLIS * 52;
        final long FIVE_MINUTE_MILLIS = MINUTE_MILLIS * 5;

        if (time < 1000000000000L) {
            time *= 1000;
        }
        long now = TimeUtil.parseToLocalMilliFromMilli(TimeUtil.currentTime());

        if (/*headerTime > now || */time <= 0) {
            String dateString = DateFormat.format("hh:mm a", new Date(now)).toString();
            return dateString;
        }

        long diff = now - time;

        //Fixme headerTime related work need to be done when the sender is using greater headerTime than receiver

        if (diff < 0L) {
            diff = -diff;
        }

        if (diff <= 2 * MINUTE_MILLIS) {
            return " " + context.getResources().getString(R.string.just_now);
        } else if (diff < 60 * MINUTE_MILLIS) {

            return diff / MINUTE_MILLIS + " " + context.getResources().getString(R.string.mins_ago);
        } else if (diff < 4 * HOUR_MILLIS) {
            if (diff >= 1 * HOUR_MILLIS && diff < 2 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + " " + context.getResources().getString(R.string.hour_ago);
            } else {
                return diff / HOUR_MILLIS + " " + context.getResources().getString(R.string.hours_ago);
            }
        } else if (diff < 24 * HOUR_MILLIS) {
            String dateString = DateFormat.format("hh:mm a", new Date(time)).toString();
            return dateString;
        } else if (diff < 7 * DAY_MILLIS) {
            return new SimpleDateFormat("EEEE").format(new Date(time));
        } else if (diff < 1 * YEAR_MILLIS) {

            int currentYear = TimeUtil.getYear(now);
            int timeYear = TimeUtil.getYear(time);

            if (currentYear != timeYear) {
                return DateFormat.format(" dd MMM yyyy ", new Date(time)).toString();
            }

            return DateFormat.format(" dd MMM", new Date(time)).toString();

        } else if (diff > 1 * YEAR_MILLIS) {
            String dateString = DateFormat.format(" dd MMM yyyy ", new Date(time)).toString();
            return dateString;
        } else {
            return diff / DAY_MILLIS + " " + context.getResources().getString(R.string.days_ago);
        }
    }

    public static View viewById(Activity activity, int viewId) {
        if (activity != null) {
            return activity.findViewById(viewId);
        }
        return null;
    }


    public static void setBackground(Activity activity, int viewId, int drawableId) {
        setBackground(getViewById(activity, viewId), drawableId);
    }

    public static void setBackground(View view, int drawableId) {
        if (view != null) {
            view.setBackgroundResource(drawableId);
        }
    }

    public static RecyclerView getRecyclerView(Fragment fragment, int resourceId) {
        return getRecyclerView(fragment.getView(), resourceId);
    }

    public static RecyclerView getRecyclerView(Activity parentView, int resourceId) {
        View view = getViewById(parentView, resourceId);

        return getRecyclerView(view, resourceId);
    }

    public static RecyclerView getRecyclerView(View parentView, int resourceId) {
        View view = getViewById(parentView, resourceId);
        if (RecyclerView.class.isInstance(view)) {
            return (RecyclerView) view;
        }
        return null;
    }

    public static RecyclerView.Adapter getAdapter(Activity activity, int resourceId) {
        return getAdapter(getRecyclerView(activity, resourceId));
    }

    public static PagerAdapter getPagerAdapter(ViewPager viewPager) {
        if (!isNull(viewPager)) {
            return viewPager.getAdapter();
        }
        return null;
    }

    public static String getText(Activity activity, int viewId) {
        View view = getViewById(activity, viewId);
        if (TextView.class.isInstance(view)) {
            return ((TextView) view).getText().toString().trim();
        }
        return null;
    }

    public static String getText(Fragment fragment, int viewId) {
        View view = getViewById(fragment, viewId);
        if (TextView.class.isInstance(view)) {
            return ((TextView) view).getText().toString().trim();
        }
        return null;
    }

    public static String getText(View parentView, int viewId) {
        View view = getViewById(parentView, viewId);
        if (TextView.class.isInstance(view)) {
            return ((TextView) view).getText().toString().trim();
        }
        return null;
    }

    public static void setImageResource(Fragment fragment, int viewId, int drawableId) {
        View view = getViewById(fragment, viewId);
        setImageResource(view, drawableId);
    }

    public static void setImageResource(View parentView, int viewId, int drawableId) {
        View view = getViewById(parentView, viewId);
        setImageResource(view, drawableId);
    }

    public static void setImageResource(View view, int drawableId) {
        if (ImageView.class.isInstance(view)) {
            ((ImageView) view).setImageResource(drawableId);
        }
    }

    public static void initRecyclerView(
            RecyclerView.LayoutManager layoutManager,
            BaseAdapter adapter,
            RecyclerClickListener.OnItemClickListener recyclerListener,
            RecyclerView recyclerView) {

        initRecyclerView(layoutManager, adapter, recyclerListener, null, null, recyclerView, false);
    }

    public static RecyclerClickListener getRecyclerListener(RecyclerView recyclerView, RecyclerClickListener.OnItemClickListener itemClickListener) {
        return new RecyclerClickListener(recyclerView, itemClickListener);
    }


    public static void initRecyclerView(
            RecyclerView.LayoutManager layoutManager,
            BaseAdapter adapter,
            RecyclerClickListener.OnItemClickListener recyclerListener,
            RecyclerClickListener.OnItemChildClickListener childListener,
            RecyclerView.OnScrollListener scrollListener,
            RecyclerView recyclerView,
            boolean enableNestedScroll) {

        if (recyclerView != null) {

            RecyclerClickListener recyclerClickListener = null;

            if (recyclerListener != null) {
                recyclerClickListener = getRecyclerListener(recyclerView, recyclerListener);
            }

/*            if (recyclerClickListener != null && childListener != null) {
                adapter.setRecyclerClickListener(recyclerClickListener);
                recyclerClickListener.setOnItemChildClickListener(childListener);
            }*/

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);
            //  recyclerView.setAdapter(adapter); // have compatible issue need to fixed later


            if (recyclerClickListener != null) {
                recyclerView.addOnItemTouchListener(recyclerClickListener);
            }

            if (scrollListener != null) {
                recyclerView.addOnScrollListener(scrollListener);
            }

            if (enableNestedScroll) {
                recyclerView.setNestedScrollingEnabled(true);
            }
        }
    }
}