package com.personal.noncommercial.significantproject.retrofitutil.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;


/**
 * 吐司工具类
 */
@SuppressLint("ShowToast")
public class ToastUtils {
    private static Toast mToast;


    public static void showToastShort(Context context, String message) {
        if (mToast == null) {
            mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(message);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }


    public static void showToastLong(Context context, int messageResId) {
        if (mToast == null) {
            mToast = Toast.makeText(context, messageResId, Toast.LENGTH_LONG);
        } else {
            mToast.setText(messageResId);
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        mToast.show();
    }
}
