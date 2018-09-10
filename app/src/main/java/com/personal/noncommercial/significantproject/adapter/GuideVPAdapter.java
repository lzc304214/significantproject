package com.personal.noncommercial.significantproject.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.personal.noncommercial.significantproject.R;

/**
 * @author :lizhengcao
 * @date :2018/3/26
 * E-mail:lizc@bsoft.com.cn
 * @类说明
 */

public class GuideVPAdapter extends PagerAdapter {


    private int[] guidePic;

    public GuideVPAdapter(int[] guidePic) {
        this.guidePic = guidePic;
    }

    @Override
    public int getCount() {
        return guidePic == null ? 0 : guidePic.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.adapter_item_vp_guide, container, false);
        ImageView iv = (ImageView) view.findViewById(R.id.iv_pager);
        iv.setImageResource(guidePic[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //不屏蔽会有异常，导致程序崩溃
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}
