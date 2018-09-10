package com.personal.noncommercial.significantproject.app;

import android.os.Environment;

/**
 * @author :lizhengcao
 * @date :2018/3/28
 * E-mail:lizc@bsoft.com.cn
 * @类说明 常量
 */

public class Constant {
    public static final String SPLASH_FIRST = "splash_first";
    public static final String LOGIN_FIRST = "login_first";
    //sd卡的路径
    public static final String SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath();
    //app存储路径
    public static final String APP_PATH = SDCARD + "/showlive";
    public static final String USERDAO_FIRST = "userdao";

}
