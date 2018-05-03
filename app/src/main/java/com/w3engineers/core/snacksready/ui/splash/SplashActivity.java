/*
*  ****************************************************************************
*  * Created by : Azizul Islam on 13-Oct-17 at 4:02 PM.
*  * Email : azizul@w3engineers.com
*  *
*  * Last edited by : Azizul Islam on 13-Oct-17.
*  *
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
*  ****************************************************************************
*/

package com.w3engineers.core.snacksready.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.w3engineers.core.snacksready.R;
import com.w3engineers.core.snacksready.data.local.appconst.AppConst;
import com.w3engineers.core.snacksready.data.remote.remotemodel.RemoteUser;
import com.w3engineers.core.snacksready.databinding.ActivitySplashBinding;
import com.w3engineers.core.snacksready.ui.base.BaseActivity;
import com.w3engineers.core.snacksready.ui.home.HomeActivity;
import com.w3engineers.core.snacksready.ui.main.MainActivity;
import com.w3engineers.core.util.helper.DialogUtil;
import com.w3engineers.core.util.helper.Logger;
import com.w3engineers.core.util.helper.Toaster;
import com.w3engineers.core.util.lib.network.NetworkService;

public class SplashActivity extends BaseActivity<SplashMvpView, SplashPresenter> implements SplashMvpView,
        NetworkService.ValidityCheckerCallBack{

    private static final int SPLASH_TIME = 2000;

    private ActivitySplashBinding activitySplashBinding;

    private final int NEW_USER = 1;
    private final int OLD_USER = 2;
    private AlertDialog loadingDialog, inputDialog;
    private int FLAG_USER_TYPE = NEW_USER;
    private boolean isRemembered;

    /**
     * Start Activity (Pass value as a 2nd Parameter)
     * Date: 2018-03-13
     * Added By: Sudipta K Paik
     *
     * @param context
     **/
    public static void runActivity(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);

        runCurrentActivity(context, intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void startUI() {
        activitySplashBinding = (ActivitySplashBinding) getViewDataBinding();
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/patchy_robots.ttf");
        activitySplashBinding.splashText.setTypeface(customFont);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.uptodown);
        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.downtoup);
        activitySplashBinding.progressBar.startAnimation(animation2);
        activitySplashBinding.splashText.startAnimation(animation1);
        activitySplashBinding.splashImage.startAnimation(animation);

        runOnUiThread(()->{
            new Handler().postDelayed(()-> {
                presenter.whereToGo();
            }, SPLASH_TIME);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        NetworkService.setValidityCheckerCallBack(this);
    }

    @Override
    protected void stopUI() {
        NetworkService.removeValidityCheckerCallBack();
    }


    @Override
    protected SplashPresenter initPresenter() {
        return new SplashPresenter();
    }

    @Override
    public void onNewSignIn() {
        runOnUiThread(this::takeInput);
    }

    @Override
    public void onValidSignIn() {
        runOnUiThread(()->{
            Toaster.show("Great!!");
            HomeActivity.runActivity(SplashActivity.this);
            overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
            finish();
        });
    }

    @Override
    public void onInvalidSignIn() {
        runOnUiThread(()->{
            Toaster.show("Invalid Office Id!");
            login();
        });
    }

    @Override
    public void onRemembered(String officeID) {
        runOnUiThread(()->{
            FLAG_USER_TYPE = OLD_USER;
            isRemembered = true;
            loadingDialog = DialogUtil.loadingDialogBuilder(this, "Checking . . .");
            presenter.checkValidity(officeID);
        });
    }

    @Override
    public void onForgot() {
        runOnUiThread(this::login);
    }

    private void login() {
        FLAG_USER_TYPE = OLD_USER;

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.prompt_office_id, null);
        EditText etOfficeId = view.findViewById(R.id.et_office_id);
        CheckBox cbRememberMe = view.findViewById(R.id.cb_remember_me);
        Button btnCancel = view.findViewById(R.id.btn_cancel);
        Button btnConfirm = view.findViewById(R.id.btn_confirm);

        inputDialog = DialogUtil.customDialogBuilder(this,
                R.string.title_log_in, view);

        btnCancel.setOnClickListener(view12 -> {
            inputDialog.dismiss();
            finish();
        });

        btnConfirm.setOnClickListener(view13 -> {
            String officeId = etOfficeId.getText().toString();
            isRemembered = cbRememberMe.isChecked();

            if(officeId == null || TextUtils.isEmpty(officeId))
                Toaster.show("Please insert your office id");
            else {
                loadingDialog = DialogUtil.loadingDialogBuilder(this, "Checking . . .");
                presenter.checkValidity(officeId);
            }
        });
    }

    int selectedAvatar = -1;
    private void takeInput(){
        FLAG_USER_TYPE = NEW_USER;

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.prompt_user_info, null);
        CircularImageView imgIcMale1 = view.findViewById(R.id.img_ic_male_1);
        CircularImageView imgIcFemale1 = view.findViewById(R.id.img_ic_female_1);
        ImageView imgIcMaleSelected1 = view.findViewById(R.id.img_ic_male_1_selected);
        ImageView imgIcFemaleSelected1 = view.findViewById(R.id.img_ic_female_1_selected);
        EditText etOfficeId = view.findViewById(R.id.et_office_id);
        Button btnCancel = view.findViewById(R.id.btn_cancel);
        Button btnConfirm = view.findViewById(R.id.btn_confirm);
        selectedAvatar = -1;

        inputDialog = DialogUtil.customDialogBuilder(this,
                R.string.title_user_info_input, view);
        imgIcMale1.setOnClickListener(view1 -> {
            if(imgIcMaleSelected1.getVisibility() == View.INVISIBLE){
                selectedAvatar = 0;
                imgIcMaleSelected1.setVisibility(View.VISIBLE);
                imgIcFemaleSelected1.setVisibility(View.INVISIBLE);
            }
            else {
                selectedAvatar = -1;
                imgIcMaleSelected1.setVisibility(View.INVISIBLE);
            }
        });
        imgIcFemale1.setOnClickListener(view1 -> {
            if(imgIcFemaleSelected1.getVisibility() == View.INVISIBLE){
                selectedAvatar = 1;
                imgIcFemaleSelected1.setVisibility(View.VISIBLE);
                imgIcMaleSelected1.setVisibility(View.INVISIBLE);
            }
            else {
                selectedAvatar = -1;
                imgIcFemaleSelected1.setVisibility(View.INVISIBLE);
            }
        });

        btnCancel.setOnClickListener(view12 -> {
            inputDialog.dismiss();
            finish();
        });

        btnConfirm.setOnClickListener(view13 -> {
            if(selectedAvatar == -1) Toaster.show("Please select an avatar");
            else {
                String officeId = etOfficeId.getText().toString();
                if(officeId == null || TextUtils.isEmpty(officeId))
                    Toaster.show("Please insert your office id");
                else {
                    loadingDialog = DialogUtil.loadingDialogBuilder(this, "Checking . . .");
                    presenter.checkValidity(officeId);
                }
            }
        });
    }

    @Override
    public void onResponse(RemoteUser remoteUser) {
        loadingDialog.dismiss();
        if(remoteUser == null || remoteUser.getSuccess() == AppConst.FAILED) {
            Toaster.show("Invalid Office Id!!");
        }
        else {
            if(inputDialog != null) inputDialog.dismiss();

            if(FLAG_USER_TYPE == NEW_USER) {
                Logger.log("processNewUser: ");
                presenter.processNewUser(remoteUser.getUser(), selectedAvatar);
            }
            else if(FLAG_USER_TYPE == OLD_USER) {
                Logger.log("processOldUser:: orderedToday: " + remoteUser.isOrderedToday());
                presenter.processOldUser(isRemembered, remoteUser.isOrderedToday());
            }
        }
    }

    @Override
    public void onFailure(String message) {
        loadingDialog.dismiss();
        Toaster.show("Error!! " + message);
        finish();
    }
}