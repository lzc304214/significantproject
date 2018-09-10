package com.personal.noncommercial.significantproject.app;

import android.app.Service;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Vibrator;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.personal.noncommercial.significantproject.greendao.UserDao;
import com.personal.noncommercial.significantproject.utils.SpUtils;
import com.personal.noncommercial.significantproject.utils.Utils;

import cn.jpush.android.api.JPushInterface;
import dalvik.system.DexClassLoader;

/**
 * @author :lizhengcao
 * @date :2018/3/27
 * E-mail:lizc@bsoft.com.cn
 * @类说明 基本的app类
 */

public class BaseApplication extends MultiDexApplication {

    private static BaseApplication mInstance;
    /**
     * 屏幕的宽
     */
    public static int screenWidth;
    /**
     * 屏幕的高度
     */
    public static int screenHeight;
    private UserDao mUserDao;


    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        mInstance = this;
        initScreenSize();
        initBaidu();
    }

    private void initBaidu() {
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(getApplicationContext());
    }

    public static Context getInstance() {
        return mInstance;
    }

    //----------------userDao--------------------------------------------

    /**
     * 得到数据库的实体类，防止每次都要
     *
     * @return
     */
    public UserDao getUserDaoInstance() {
        if (mUserDao == null)
            mUserDao = new UserDao(Utils.getContext());
        return mUserDao;
    }

    /**
     * 是否是第一次进入到userDao数据库中
     */
    public void setIsFirstUserDaoData(boolean isFirst) {
        SpUtils.putBoolean(Utils.getContext(), Constant.USERDAO_FIRST, isFirst);
    }

    public boolean getIsFirstUserDaoData() {
        return SpUtils.getBoolean(Utils.getContext(), Constant.USERDAO_FIRST, true);
    }

    //----------------userDao--------------------------------------------

    /**
     * 初始化当前屏幕的宽高
     */
    private void initScreenSize() {
        DisplayMetrics outMetrics = getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = outMetrics.widthPixels;
        screenHeight = outMetrics.heightPixels;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public String getVersion() {

        try {
            PackageManager manager = mInstance.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mInstance.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }
}
