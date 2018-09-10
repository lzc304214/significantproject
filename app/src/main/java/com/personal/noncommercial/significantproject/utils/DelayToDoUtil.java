package com.personal.noncommercial.significantproject.utils;

import android.os.Handler;

import com.personal.noncommercial.significantproject.utils.listener.OnDelayListener;

/**
 * @author :lizhengcao
 * @date :2018/8/7
 * E-mail:lizc@bsoft.com.cn
 * @类说明 延时去做工具类
 */

public class DelayToDoUtil {

    private static Handler handler;

    public static void delayToDo(long delayTime, final OnDelayListener onDelayListener) {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //execute the task
                onDelayListener.doSomething();
            }
        }, delayTime);
    }

    /**
     * 如果当延迟的时间未执行完毕，直接返回上个界面，会出问题
     */
    public static void cancelHandler() {
        if (handler == null)
            return;
        handler.removeCallbacksAndMessages(null);
    }
}
