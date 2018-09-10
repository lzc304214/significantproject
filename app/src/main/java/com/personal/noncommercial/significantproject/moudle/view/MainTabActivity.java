package com.personal.noncommercial.significantproject.moudle.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.adapter.MainTabViewPager;
import com.personal.noncommercial.significantproject.moudle.base.BaseActivity;
import com.personal.noncommercial.significantproject.view.bottomtab.AlphaTabsIndicator;

import butterknife.BindView;

/**
 * @author :lizhengcao
 * @date :2018/3/27
 * E-mail:lizc@bsoft.com.cn
 * @类说明 主页面
 */

public class MainTabActivity extends BaseActivity {


    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.alpha_tab)
    AlphaTabsIndicator mAlphaTabsIndicator;


    @Override
    protected void initOnCreate(@Nullable Bundle savedInstanceState) {
        //适配器  viewPager与bottomTab的结合
        MainTabViewPager mainTabViewPager = new MainTabViewPager(getSupportFragmentManager());
        mViewPager.setAdapter(mainTabViewPager);
        mAlphaTabsIndicator.setViewPager(mViewPager);
    }

    @Override
    protected boolean isHideBack() {
        return true;
    }

    @Override
    protected boolean isHideToolbar() {
        return true;
    }

    @Override
    protected int getRootLayout() {
        return R.layout.activity_main;
    }
}
