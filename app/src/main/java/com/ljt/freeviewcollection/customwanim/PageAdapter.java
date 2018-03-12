package com.ljt.freeviewcollection.customwanim;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by ${JT.L} on 2018/3/9.
 */

public class PageAdapter extends FragmentPagerAdapter {

    private List<PageFragment> fragments;
    public PageAdapter(FragmentManager fm,List<PageFragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
