package com.personal.noncommercial.significantproject.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.personal.noncommercial.significantproject.factory.FragmentFactory;

/**
 * @author :lizhengcao
 * @date :2018/3/29
 * E-mail:lizc@bsoft.com.cn
 * @类说明
 */

public class MainTabViewPager extends FragmentPagerAdapter {

    public MainTabViewPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.getMainTabInstance(position);
    }

    @Override
    public int getCount() {
        return FragmentFactory.MAIN_TAB_FRAGMENT_COUNT;
    }
}
