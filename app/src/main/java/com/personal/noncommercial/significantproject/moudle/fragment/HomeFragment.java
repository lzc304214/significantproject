package com.personal.noncommercial.significantproject.moudle.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.adapter.HomeRvAdapter;
import com.personal.noncommercial.significantproject.factory.FragmentFactory;
import com.personal.noncommercial.significantproject.moudle.base.BaseFragment;
import com.personal.noncommercial.significantproject.moudle.bean.HomeUIBean;
import com.personal.noncommercial.significantproject.moudle.view.BaiDuMapActivity;
import com.personal.noncommercial.significantproject.moudle.view.GaoDeLocationActivity;
import com.personal.noncommercial.significantproject.moudle.view.PayAliAndWXActivity;
import com.personal.noncommercial.significantproject.moudle.view.EventBusFucActivity;
import com.personal.noncommercial.significantproject.moudle.view.H5CallActivity;
import com.personal.noncommercial.significantproject.moudle.view.JavaAndJsActivity;
import com.personal.noncommercial.significantproject.moudle.view.PhotoActivity;
import com.personal.noncommercial.significantproject.moudle.view.PhotoActivity2;
import com.personal.noncommercial.significantproject.moudle.view.RecyRelationActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;


/**
 * @author :lizhengcao
 * @date :2018/3/29
 * E-mail:lizc@bsoft.com.cn
 * @类说明 首页
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private static final int SPAN_COUNT = 4;
    //图片
    private int[] images = {
            R.mipmap.home_bgcx_n, R.mipmap.home_pdjh_n, R.mipmap.home_yrqd_n,
            R.mipmap.home_yygh_n, R.mipmap.home_tjbg, R.mipmap.home_bgcx_n,
            R.mipmap.home_pdjh_n, R.mipmap.home_bgcx_n, R.mipmap.home_pdjh_n};
    //文本
    private String[] bottomText = {
            "图片", "图片2", "java与js",
            "eventBus", "调用H5", "列表", "支付", "百度定位", "高德定位"};
    //类
    private Class<?>[] activity = {
            PhotoActivity.class, PhotoActivity2.class, JavaAndJsActivity.class,
            EventBusFucActivity.class, H5CallActivity.class, RecyRelationActivity.class,
            PayAliAndWXActivity.class, BaiDuMapActivity.class, GaoDeLocationActivity.class};
    //数据集合
    private List<HomeUIBean> mHomeUIList;
    //适配器
    private HomeRvAdapter homeRvAdapter;

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getRootLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initOnViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setCurrentFragmentTitle(mainTab[FragmentFactory.HOME_FRAGMENT_POSITION]);

        //数据
        mHomeUIList = new ArrayList<>();

        for (int i = 0; i < images.length; i++) {
            mHomeUIList.add(new HomeUIBean(images[i], bottomText[i]));
        }

        //适配器
        homeRvAdapter = new HomeRvAdapter();
        mRecyclerView.setAdapter(homeRvAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getBaseActivity(), SPAN_COUNT));

        homeRvAdapter.setNewData(mHomeUIList);


        //监听
        homeRvAdapter.setOnItemClickListener((baseQuickAdapter, view1, position) ->
                lauch(activity[position]));
    }

}
