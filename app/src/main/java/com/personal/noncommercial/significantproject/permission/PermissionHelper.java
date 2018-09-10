package com.personal.noncommercial.significantproject.permission;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

/**
 * @author :lizhengcao
 * @date :2018/3/30
 * E-mail:lizc@bsoft.com.cn
 * @类说明 6.0权限类
 */

public class PermissionHelper {
    //定位
    public static final int REQUEST_LOCATION_PERMISSION = 1;
    //相机
    public static final int REQUEST_CAMERA_AND_WRITE_ES_PERMISSION = 2;
    //相册
    public static final int REQUEST_WRITE_ES_PERMISSION = 3;
    //支付
    public static final int REQUEST_READ_PHONE_STATE_AND_WRITE_EXTERNAL_STORAGE = 4;

    /**
     * 检查权限
     *
     * @param activity
     * @param requestCode
     * @return true--权限已通过  false--权限已禁止
     */


    @TargetApi(Build.VERSION_CODES.M)
    public static boolean checkPermission(Activity activity, int requestCode) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                //百度定位权限 （获取手机状态  获取位置信息 读写SD卡）
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) !=
                                PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                                PackageManager.PERMISSION_GRANTED
                        ) {
                    //未授权
                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION) ||
                            ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_PHONE_STATE) ||
                            ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        //禁止权限，下次调用时请求权限
                        activity.requestPermissions(new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
                        return false;
                    } else {
                        //默认第一次调用时，请求权限
                        activity.requestPermissions(new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
                        return false;
                    }

                }
                break;
            case REQUEST_CAMERA_AND_WRITE_ES_PERMISSION:
                //相机权限
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) !=
                        PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                                PackageManager.PERMISSION_GRANTED) {
                    //未授权
                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA) ||
                            ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        //禁止
                        activity.requestPermissions(new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
                        return false;
                    } else {
                        //默认
                        activity.requestPermissions(new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
                        return false;
                    }
                }
                break;
            case REQUEST_WRITE_ES_PERMISSION:
                //相册
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //未授权
                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        //禁止
                        activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
                        return false;
                    } else {
                        //默认
                        activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
                        return false;
                    }
                }
                break;
            case REQUEST_READ_PHONE_STATE_AND_WRITE_EXTERNAL_STORAGE:
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //未授权
                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_PHONE_STATE) ||
                            ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        //禁止
                        activity.requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
                        return false;
                    } else {
                        //默认
                        activity.requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
                        return false;
                    }
                }
                break;
        }
        return true;
    }

    //检查所有权限结果
    public static boolean checkAllPermissionResult(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    //增加照片所需要的对话框
    public static void addCameraPermissionDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("权限")
                .setMessage("调用相机权限被禁止")
                .setPositiveButton("确定", null)
                .show();
    }

    //增加照片所需要的对话框
    public static void addAlbumPermissionDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("权限")
                .setMessage("调用相册权限被禁止")
                .setPositiveButton("确定", null)
                .show();
    }

    //定位所需要的对话框
    public static void addLocationPermissionDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("权限")
                .setMessage("调用定位权限被禁止")
                .setPositiveButton("确定", null)
                .show();
    }

    //支付
    public static void addPayPermissionDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("权限")
                .setMessage("调用支付权限被禁止")
                .setPositiveButton("确定", null)
                .show();
    }

}
