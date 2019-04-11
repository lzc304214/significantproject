package com.personal.noncommercial.significantproject.viewModel;

import android.content.Context;
import android.databinding.ObservableField;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.personal.noncommercial.significantproject.utils.ToastUtils;

/**
 * @author :lizhengcao
 * @date :2019/4/7
 * E-mail:lizc@bsoft.com.cn
 * @类说明
 */
public class VMModel {

    private Context mContext;
    public ObservableField<String> field = new ObservableField<>();

    public VMModel(Context context) {
        this.mContext = context;
        initData();
    }

    private void initData() {
        field.set("field字段");
    }

    public void click(View v) {
        ToastUtils.showToastShort("点击了");
    }


}
