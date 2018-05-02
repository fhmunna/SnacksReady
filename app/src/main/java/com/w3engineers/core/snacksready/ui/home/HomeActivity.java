package com.w3engineers.core.snacksready.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

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
import com.w3engineers.core.snacksready.ui.base.BaseActivity;
import com.w3engineers.core.snacksready.ui.main.MainActivity;
import com.w3engineers.core.snacksready.ui.profile.ProfileFragment;

public class HomeActivity extends BaseActivity<HomeMvpView, HomePresenter> implements HomeMvpView {
    private final int HOME = 1, ORDER_SNACK = 2, CONFIRM_LUNCH = 3, SNACKS_REMAINDER = 4, LOGOUT = 5;

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
    private Drawer drawer;
    private AccountHeader accountHeader;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment fragment;

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

        //replaceFragment(ProfileFragment.newInstance("Profile"));
    }

    @Override
    protected void stopUI() {

    }

    @Override
    protected HomePresenter initPresenter() {
        return new HomePresenter();
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
                        itemSnacksRemainder
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

        itemOrderSnacks.withBadge("1 hour left!").withBadgeStyle(new BadgeStyle().withColor(Color.RED));
        itemConfirmLunch.withBadge("1 day left!").withBadgeStyle(new BadgeStyle().withColor(Color.RED));
    }

    private void onChangeSelection(int position){
        switch (position){
            case HOME:
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
            if(fragmentManager == null) fragmentManager = getSupportFragmentManager();
            if( fragmentTransaction == null) fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.commit();
        }
    }
}
