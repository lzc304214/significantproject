package com.personal.noncommercial.significantproject.adapter;

import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.app.BaseApplication;
import com.personal.noncommercial.significantproject.utils.Utils;


/**
 * @author :lizhengcao
 * @date :2018/5/21
 * E-mail:lizc@bsoft.com.cn
 * @类说明
 */

public class MultiItemQuickChildAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private int picWidth;

    public MultiItemQuickChildAdapter() {
        super(R.layout.adapter_child_quick_item_multi);
        picWidth = BaseApplication.screenWidth / 3;

    }

    @Override
    protected void convert(BaseViewHolder holder, String s) {
        ImageView ivPic = holder.getView(R.id.iv_picture);
        ViewGroup.LayoutParams params = ivPic.getLayoutParams();
        params.width = picWidth;
        params.height = picWidth;
        ivPic.setLayoutParams(params);
        Glide.with(holder.itemView.getContext())
                .load(s)
                .asBitmap()
                .placeholder(R.mipmap.defalut)
                .error(R.mipmap.error_icon)
                .into(ivPic);
    }
}
