package com.w3engineers.core.snacksready.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.w3engineers.core.snacksready.ui.profile.ProfileFragment;
import com.w3engineers.core.snacksready.ui.snacks.SnacksFragment;


/*
*  ****************************************************************************
*  * Created by : Md. Hasnain on 4/18/2018 at 6:23 PM.
*  * Email : ashik.pstu.cse@gmail.com
*  * 
*  * Last edited by : Md. Hasnain on 4/18/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/

public class PagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 2;
    private String[] titles = {"Today's RemoteSnacks", "Profile"};

    public PagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }


    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return SnacksFragment.newInstance(titles[0]);

            case 1:
                return ProfileFragment.newInstance(titles[1]);

            default:
            return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

}