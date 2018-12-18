package com.personal.noncommercial.significantproject.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.moudle.bean.DataEntity;
import com.personal.noncommercial.significantproject.moudle.bean.ItemDataEntity;
import com.personal.noncommercial.significantproject.moudle.inter.OnLeftAndRightListener;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author :lizhengcao
 * @date :2018/12/11
 * E-mail:lizc@bsoft.com.cn
 * @类说明 右边的列表
 */

public class RightRecAdapter extends BaseQuickAdapter<ItemDataEntity, BaseViewHolder> {
    @BindView(R.id.tv_right)
    TextView mTvRight;

    public RightRecAdapter() {
        super(R.layout.adapter_rec_right);
    }

    @Override
    protected void convert(BaseViewHolder helper, ItemDataEntity item) {
        ButterKnife.bind(this, helper.itemView);
        mTvRight.setText(item.getInclude());

    }


}
