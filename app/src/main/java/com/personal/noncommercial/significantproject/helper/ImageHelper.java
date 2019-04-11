package com.personal.noncommercial.significantproject.helper;

import android.databinding.BindingAdapter;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

/**
 * @author :lizhengcao
 * @date :2019/4/6
 * E-mail:lizc@bsoft.com.cn
 * @类说明
 */
public class ImageHelper {

    @BindingAdapter({"resId"})
    public static void loadDrawableResource(ImageView iv, int resId) {
        iv.setImageResource(resId);
    }
}
