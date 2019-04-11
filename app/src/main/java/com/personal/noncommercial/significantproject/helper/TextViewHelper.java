package com.personal.noncommercial.significantproject.helper;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.TextView;

import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.utils.Utils;

import java.util.Calendar;

/**
 * @author :lizhengcao
 * @date :2019/4/7
 * E-mail:lizc@bsoft.com.cn
 * @类说明 1 红色 2 black 3 green
 */
public class TextViewHelper {


    @BindingAdapter({"colorType"})
    public static void setTextViewColorRes(TextView tv, int colorType) {
        switch (colorType) {
            case 1:
                tv.setTextColor(getResColor(R.color.red_round));
                break;
            case 2:
                tv.setTextColor(getResColor(R.color.black));
                break;
            case 3:
                tv.setTextColor(getResColor(R.color.colorAccent));
                break;
        }

    }

    private static int getResColor(int color) {
        return Utils.getContext().getResources().getColor(color);
    }
}
