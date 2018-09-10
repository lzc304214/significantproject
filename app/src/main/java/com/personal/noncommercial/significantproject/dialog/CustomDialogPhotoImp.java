package com.personal.noncommercial.significantproject.dialog;

import android.content.Context;
import android.content.DialogInterface;

import com.personal.noncommercial.significantproject.moudle.inter.OnPhotoJiuGongPic;

/**
 * @author :lizhengcao
 * @date :2018/4/11
 * E-mail:lizc@bsoft.com.cn
 * @类说明
 */

public class CustomDialogPhotoImp {

    private Context mContext;
    private OnPhotoJiuGongPic mPhotoJiuGongPicListener;

    public CustomDialogPhotoImp(Context context, OnPhotoJiuGongPic photoJiuGongPicListener) {
        this.mContext = context;
        this.mPhotoJiuGongPicListener = photoJiuGongPicListener;
    }

    public void setCustomDialog(final int position) {
        new CustomDialog.Builder(mContext)
                .setTitle("提示")
                .setMessage("确认删除该图片")
                .setPositiveButton("取消", (dialog, which) -> dialog.dismiss())
                .setNegativeButton("确定", (dialog, which) -> {
                    mPhotoJiuGongPicListener.deletePhotoItem(position);
                    dialog.dismiss();
                }).build().show();
    }
}
