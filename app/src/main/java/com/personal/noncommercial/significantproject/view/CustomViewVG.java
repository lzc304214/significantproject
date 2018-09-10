package com.personal.noncommercial.significantproject.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.personal.noncommercial.significantproject.utils.DensityUtils;

/**
 * @author :lizhengcao
 * @date :2018/7/31
 * E-mail:lizc@bsoft.com.cn
 * @类说明 自己专门练习的自定义view
 */

public class CustomViewVG extends ViewGroup {

    private static final String TAG = CustomViewVG.class.getSimpleName();

    private static final int OFFSET = DensityUtils.dp2px(60);

    public CustomViewVG(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量自身的大小以及其child的大小规格
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;

        //给每个孩子测量规格
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            LayoutParams lp = child.getLayoutParams();
            int childWidthMeasure = getChildMeasureSpec(widthMeasureSpec, 0, lp.width);
            int childHeightMeasure = getChildMeasureSpec(heightMeasureSpec, 0, lp.height);
            child.measure(childWidthMeasure, childHeightMeasure);
        }

        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                for (int i = 0; i < childCount; i++) {
                    View child = getChildAt(i);
                    int widthAndOffset = i * OFFSET + child.getMeasuredWidth();
                    width = Math.max(width, widthAndOffset);
                }
                break;
            default:
                break;
        }


        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                for (int i = 0; i < childCount; i++) {
                    View child = getChildAt(i);
                    height = height + child.getMeasuredHeight();
                }
                break;
            default:
                break;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //摆放
        int left = 0;
        int right = 0;
        int top = 0;
        int bottom = 0;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            left = i * OFFSET;
            right = left + child.getMeasuredWidth();
            bottom = top + child.getMeasuredHeight();
            child.layout(left, top, right, bottom);
            top += child.getMeasuredHeight();
        }
    }
}
