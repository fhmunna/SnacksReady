package com.w3engineers.core.util.helper;

/*
* Last modified by Md. Hasnain
* 0n 04/08/18 12.54AM
*
* Modification -> added a new parameter 'flag'
* -> added red color with positive text for delete flag
*
* Reason -> if we use same dialog for more that one time in same activity/fragment we need to be sure
* for which flag we are using which dialog.
*/

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;

import com.w3engineers.core.snacksready.R;

public class DialogUtil {
    public static final int FLAG_EDIT = 1;
    public static final int FLAG_DELETE = 2;
    public static final int FLAG_RESET = 3;
    public static final int FLAG_DOWNLOAD = 4;
    public static final int FLAG_ADD_TO_FAVOURITE = 5;
    public static final int FLAG_LOG_OUT = 6;

    private static String COLOR_CODE = "#000000";

    public static void showConfirmationDialog(Context context,
                                              String title,
                                              String message,
                                              String positiveText,
                                              String negativeText,
                                              final DialogButtonListener listener,
                                              final int flag) {
        try {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.DefaultAlertDialogStyle);
            alertDialogBuilder.setTitle(Html.fromHtml("<b>" + title + "</b>"));
            alertDialogBuilder.setMessage(Html.fromHtml("<font color='#757575'>" + message + "</font>"));

            if(flag == FLAG_DELETE) COLOR_CODE = "#d34836";

            if (positiveText != null) {
                alertDialogBuilder.setPositiveButton(Html.fromHtml(
                        "<font color='" + COLOR_CODE +"'>" + positiveText + "</font>"),
                        (dialog, whichButton) -> listener.onClickPositive(flag));
            }
            if (negativeText != null) {
                alertDialogBuilder.setNegativeButton(negativeText, (dialog, which) -> listener.onClickNegative(flag));
            }


            alertDialogBuilder.setOnCancelListener(dialog -> listener.onCancel(flag));
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static AlertDialog.Builder dialogBuilder(Context context, View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (view != null) {
            builder.setView(view);
        }

        return builder;
    }

    public static AlertDialog messageDialogBuilder(Context context, int title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (title > 0) {
            builder.setTitle(title);
        }
        builder.setMessage(message)
                .setPositiveButton("Hide", (dialogInterface, i) -> dialogInterface.dismiss());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        return alertDialog;
    }

    public static AlertDialog loadingDialogBuilder(Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_loading_star, null);
        if (view != null) {
            builder.setView(view);
        }

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(true);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();

        return alertDialog;
    }

    public static AlertDialog customDialogBuilder(Context context, int title, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (view != null) {
            builder.setView(view);
        }
        if (title > 0) {
            builder.setTitle(title);
        }
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        return alertDialog;
    }

    public interface DialogButtonListener {
        void onClickPositive(int flag);

        void onCancel(int flag);

        void onClickNegative(int flag);
    }
}
