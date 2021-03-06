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
import com.w3engineers.core.snacksready.databinding.ActivitySplashBinding;
import com.w3engineers.core.snacksready.ui.base.BaseActivity;
import com.w3engineers.core.snacksready.ui.main.MainActivity;
import com.w3engineers.core.util.helper.DialogUtil;
import com.w3engineers.core.util.helper.Toaster;

public class SplashActivity extends BaseActivity<SplashMvpView, SplashPresenter> implements SplashMvpView{

    private static final int SPLASH_TIME = 2300;

    private ActivitySplashBinding activitySplashBinding;

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
        Animation topDown = AnimationUtils.loadAnimation(this, R.anim.updown);
        Animation downTop = AnimationUtils.loadAnimation(this, R.anim.downtop);
        activitySplashBinding.imgAppName.setAnimation(topDown);
        activitySplashBinding.tvAppName.setAnimation(downTop);

        runOnUiThread(()->{
            new Handler().postDelayed(()-> {
                presenter.whereToGo();
            }, SPLASH_TIME);
        });
    }

    @Override
    protected void stopUI() {

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
            MainActivity.runActivity(SplashActivity.this);
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
    public void onForgot() {
        runOnUiThread(this::login);
    }

    private void login() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.prompt_office_id, null);
        EditText etOfficeId = view.findViewById(R.id.et_office_id);
        CheckBox cbRememberMe = view.findViewById(R.id.cb_remember_me);
        Button btnCancel = view.findViewById(R.id.btn_cancel);
        Button btnConfirm = view.findViewById(R.id.btn_confirm);

        AlertDialog alertDialog = DialogUtil.customDialogBuilder(this,
                R.string.title_log_in, view);

        btnCancel.setOnClickListener(view12 -> {
            alertDialog.dismiss();
            finish();
        });

        btnConfirm.setOnClickListener(view13 -> {
            String officeId = etOfficeId.getText().toString();
            if(officeId == null || TextUtils.isEmpty(officeId))
                Toaster.show("Please insert your office id");
            else {
                presenter.checkSignInValidity(officeId, cbRememberMe.isChecked());
            }
        });
    }

    int selectedAvatar;
    private void takeInput(){
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

        AlertDialog alertDialog = DialogUtil.customDialogBuilder(this,
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
            alertDialog.dismiss();
            finish();
        });

        btnConfirm.setOnClickListener(view13 -> {
            if(selectedAvatar == -1) Toaster.show("Please select an avatar");
            else {
                String officeId = etOfficeId.getText().toString();
                if(officeId == null || TextUtils.isEmpty(officeId))
                    Toaster.show("Please insert your office id");
                else {
                    Toaster.show("Great!!");
                    presenter.saveUserInfo(selectedAvatar, officeId);
                }
            }
        });
    }
}