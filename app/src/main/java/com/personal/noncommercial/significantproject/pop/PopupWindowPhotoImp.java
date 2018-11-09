package com.personal.noncommercial.significantproject.pop;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.moudle.inter.OnCameraAndAlbum;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author :lizhengcao
 * @date :2018/4/2
 * E-mail:lizc@bsoft.com.cn
 * @类说明 关于popWindow的图片的实现类
 */

public class PopupWindowPhotoImp {

    private Context mContext;
    private CustomPopWindow.PopupWindowBuilder builder;
    private CustomPopWindow customPopWindow;
    private OnCameraAndAlbum cameraAndAlbum;

    public PopupWindowPhotoImp(Context context, OnCameraAndAlbum cameraAndAlbum) {
        this.mContext = context;
        this.cameraAndAlbum = cameraAndAlbum;
    }

    public void setPopWindow() {
        //填充布局
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_pop_photo_view, null);
        if (builder == null) {
            builder = new CustomPopWindow.PopupWindowBuilder(mContext);
        }
        //设置布局
        builder.setView(view);
        //找控件
        TextView tvCamera = (TextView) view.findViewById(R.id.tv_camera);
        TextView tvAlbum = (TextView) view.findViewById(R.id.tv_album);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);

        builder.enableBackgroundDark(true);
        builder.setBgDarkAlpha(0.7f);
        customPopWindow = builder.create();

        View v = new View(mContext);
        customPopWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);

        //相机
        tvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraAndAlbum.onCamera();
                customPopWindow.dissmiss();
            }
        });

        tvAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraAndAlbum.onAlbum();
                customPopWindow.dissmiss();

            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customPopWindow.dissmiss();
            }
        });
    }


}
