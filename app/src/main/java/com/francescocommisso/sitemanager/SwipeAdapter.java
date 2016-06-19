package com.francescocommisso.sitemanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SwipeAdapter extends FragmentPagerAdapter{
    public SwipeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new PageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("count",position+1);
        return fragment;
    }

    @Override
    public int getCount() {
        return 1;
    }
}
