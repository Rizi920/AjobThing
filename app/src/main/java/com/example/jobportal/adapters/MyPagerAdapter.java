package com.example.jobportal.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.jobportal.ui.fragments.ForYouTabFragment;
import com.example.jobportal.ui.fragments.SavedTabFragment;

public class MyPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 2;

    public MyPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return ForYouTabFragment.newInstance("", "");
            case 1: // Fragment # 1 - This will show SecondFragment
                return SavedTabFragment.newInstance("", "");
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "For You";
            case 1:
                return "Saved";
            default:
                return null;
        }

    }

}
