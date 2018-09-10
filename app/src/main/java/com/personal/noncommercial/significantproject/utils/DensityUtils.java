package com.personal.noncommercial.significantproject.utils;

import android.content.Context;

import com.personal.noncommercial.significantproject.app.BaseApplication;

/**
 * @author :lizhengcao
 * @date :2017/6/7
 * E-mail:lizc@bsoft.com.cn
 * @类说明 dp与px的转化工具类
 */

public class DensityUtils {

    public static Context getContext() {
        return BaseApplication.getInstance();
    }

    public static float getDensity() {
        return getContext().getResources().getDisplayMetrics().density;
    }

    /**
     * dp转化为px
     */
    public static int dp2px(float dp) {
        return (int) (dp * getDensity() + 0.5f);
    }

    /**
     * px转化为dp
     */
    public static float px2dp(int px) {
        return px / getDensity();
    }
}
