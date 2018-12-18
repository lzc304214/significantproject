package com.personal.noncommercial.significantproject.adapter;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.moudle.bean.DataEntity;
import com.personal.noncommercial.significantproject.moudle.bean.RecyclerItemEntity;
import com.personal.noncommercial.significantproject.moudle.inter.OnLeftAndRightListener;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author :lizhengcao
 * @date :2018/12/11
 * E-mail:lizc@bsoft.com.cn
 * @类说明 左边的列表
 */

public class LeftRecAdapter extends BaseQuickAdapter<DataEntity, BaseViewHolder> {
    @BindView(R.id.tv_left)
    TextView mTvLeft;
    private OnLeftAndRightListener mListener;

    public LeftRecAdapter(OnLeftAndRightListener listener) {
        super(R.layout.adapter_rec_left);
        this.mListener = listener;
    }

    @Override
    protected void convert(BaseViewHolder helper, DataEntity item) {
        ButterKnife.bind(this, helper.itemView);
        mTvLeft.setText(item.getAddress());
        int pos = helper.getAdapterPosition();
        mListener.onLeftChildPosListener(pos,helper.itemView);

    }


}
