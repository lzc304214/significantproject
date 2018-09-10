package com.personal.noncommercial.significantproject.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.moudle.bean.RecylerListEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author :lizhengcao
 * @date :2018/5/18
 * E-mail:lizc@bsoft.com.cn
 * @类说明
 */

public class RecylerRelationAdapter extends BaseQuickAdapter<RecylerListEntity, BaseViewHolder> {

    @BindView(R.id.tv_header)
    TextView mTvHeader;

    public RecylerRelationAdapter() {
        super(R.layout.adapter_item_relation_recyler);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, RecylerListEntity data) {
        ButterKnife.bind(this, baseViewHolder.itemView);
        mTvHeader.setText(data.getHead());
    }
}
