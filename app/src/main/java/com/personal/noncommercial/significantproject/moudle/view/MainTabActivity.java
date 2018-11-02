package com.personal.noncommercial.significantproject.moudle.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.adapter.MainTabViewPager;
import com.personal.noncommercial.significantproject.moudle.base.BaseActivity;
import com.personal.noncommercial.significantproject.retrofitutil.model.ResultModel;
import com.personal.noncommercial.significantproject.retrofitutil.model.UpdateModel;
import com.personal.noncommercial.significantproject.retrofitutil.net.ApiService;
import com.personal.noncommercial.significantproject.retrofitutil.net.OnResponseListener;
import com.personal.noncommercial.significantproject.view.bottomtab.AlphaTabsIndicator;

import butterknife.BindView;
import retrofit2.Call;

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

        //网路请求的加载测试案例
        Call<ResultModel<UpdateModel>> call = ApiService.newInstance().getApiInterface().getNewAppVersion("android_pub", "15");
        call.enqueue(new OnResponseListener<ResultModel<UpdateModel>>(mContext) {
            @Override
            public void onSuccess(ResultModel<UpdateModel> data) {
                Log.e("网路请求的加载：", "请求数据成功了");
            }

            @Override
            protected boolean needLoadingDialog() {
                return false;
            }
        });
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
