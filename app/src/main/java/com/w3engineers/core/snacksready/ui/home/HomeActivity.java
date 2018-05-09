package com.w3engineers.core.snacksready.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.w3engineers.core.snacksready.R;
import com.w3engineers.core.snacksready.ui.admin.AdminFragment;
import com.w3engineers.core.snacksready.ui.base.BaseActivity;
import com.w3engineers.core.snacksready.ui.main.MainActivity;
import com.w3engineers.core.snacksready.ui.profile.ProfileFragment;
import com.w3engineers.core.util.helper.TimeUtil;
import com.w3engineers.core.util.helper.Toaster;

import java.util.Calendar;

public class HomeActivity extends BaseActivity<HomeMvpView, HomePresenter> implements HomeMvpView {
    private final int HOME = 1, ORDER_SNACK = 2, CONFIRM_LUNCH = 3, SNACKS_REMAINDER = 4, ADMIN = 5, LOGOUT = 6;

    //Drawer items
    private PrimaryDrawerItem itemHome = new PrimaryDrawerItem()
            .withIdentifier(HOME)
            .withName(R.string.drawer_item_home)
            .withIcon(FontAwesome.Icon.faw_home);
    private SecondaryDrawerItem itemOrderSnacks = new SecondaryDrawerItem().withName(R.string.drawer_item_order_snacks)
            .withIdentifier(ORDER_SNACK)
            .withIcon(FontAwesome.Icon.faw_coffee);
    private SecondaryDrawerItem itemConfirmLunch = new SecondaryDrawerItem().withName(R.string.drawer_item_confirm_lunch)
            .withIdentifier(CONFIRM_LUNCH)
            .withIcon(FontAwesome.Icon.faw_smile);
    private SwitchDrawerItem itemSnacksRemainder = new SwitchDrawerItem().withName(R.string.drawer_item_alarm)
            .withIdentifier(SNACKS_REMAINDER)
            .withIcon(FontAwesome.Icon.faw_bell)
            .withChecked(true);
    private SecondaryDrawerItem itemAdmin = new SecondaryDrawerItem().withName(R.string.drawer_item_admin)
            .withIdentifier(ADMIN)
            .withIcon(FontAwesome.Icon.faw_user_secret);
    private Drawer drawer;
    private AccountHeader accountHeader;

    public static void runActivity(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);

        runCurrentActivity(context, intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void startUI() {
        initView();

        replaceFragment(ProfileFragment.newInstance("Profile"));
    }

    @Override
    protected void stopUI() {

    }

    @Override
    protected HomePresenter initPresenter() {
        return new HomePresenter();
    }

    @Override
    public void onLoadLocalData(boolean isSnacksOrdered, boolean isLunchConfirmed,
                                boolean isRemainderSet, String timeLeftSnacks, String badgeLunch) {
        runOnUiThread(()->{
            int color = Color.RED;
            if(isSnacksOrdered) color = Color.BLUE;
            itemOrderSnacks.withBadge(timeLeftSnacks).withBadgeStyle(new BadgeStyle().withColor(color));
            itemSnacksRemainder.withChecked(isRemainderSet);

            if(!isLunchConfirmed) color = Color.RED;
            else color = Color.BLUE;
            itemConfirmLunch.withBadge(badgeLunch).withBadgeStyle(new BadgeStyle().withColor(color));
        });
    }

    private void initView(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.drawer_bg)
                .addProfiles(
                        new ProfileDrawerItem().withName("Arhan Ashik").withEmail("ashik.pstu.cse@gmail.com").withIcon(getResources().getDrawable(R.drawable.ic_male1))
                )
                .withOnAccountHeaderListener((view, profile, currentProfile) -> false)
                .build();

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(accountHeader)
                .addDrawerItems(
                        itemHome,
                        new DividerDrawerItem(),
                        itemOrderSnacks,
                        itemConfirmLunch,
                        new DividerDrawerItem(),
                        itemSnacksRemainder,
                        new DividerDrawerItem(),
                        itemAdmin
                )
                .withSelectedItem(-1)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    onChangeSelection((int) drawerItem.getIdentifier());
                    return false;
                })
                .build();

        drawer.addStickyFooterItem(new PrimaryDrawerItem().withName("LOG OUT")
                .withIdentifier(LOGOUT).withIcon(FontAwesome.Icon.faw_sign_out_alt));
        drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);

        itemConfirmLunch.withBadge("Coming soon!").withBadgeStyle(new BadgeStyle().withColor(Color.WHITE)
                .withTextColor(Color.BLACK));

        itemSnacksRemainder.withOnCheckedChangeListener((drawerItem, buttonView, isChecked) -> {
            if(isChecked){
                Toaster.show("Remainder set!");
                presenter.setSnacksRemainder();
            }
            else {Toaster.show("Remainder stopped!");
                presenter.stopSnacksRemainder();
            }
        });

        presenter.loadLocalData();
    }

    private void onChangeSelection(int position){
        Fragment fragment = null;
        switch (position){
            case HOME:
                setTitle("Home");
                fragment = ProfileFragment.newInstance("Profile");
                break;

            case ORDER_SNACK:
                MainActivity.runActivityWithFlag(this, 0);
                break;

            case CONFIRM_LUNCH:
                MainActivity.runActivityWithFlag(this, 1);
                break;

            case SNACKS_REMAINDER:
                break;

            case ADMIN:
                setTitle("Admin");
                fragment = AdminFragment.newInstance("Admin");
                break;

            case LOGOUT:
                fragment = null;
                break;

            default:
                fragment = ProfileFragment.newInstance("Profile");
                break;
        }

        replaceFragment(fragment);
    }

    private void replaceFragment(Fragment fragment){
        if(fragment != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment);
            //fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
