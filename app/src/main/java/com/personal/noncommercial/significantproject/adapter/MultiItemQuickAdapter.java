package com.personal.noncommercial.significantproject.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.moudle.bean.MultiDataItem;
import com.personal.noncommercial.significantproject.utils.DensityUtils;
import com.personal.noncommercial.significantproject.utils.Utils;
import com.personal.noncommercial.significantproject.view.manager.FullyGridLayoutManager;
import com.personal.noncommercial.significantproject.view.weight.SpacesItemDecoration;

import java.util.List;


/**
 * @author :lizhengcao
 * @date :2018/5/21
 * E-mail:lizc@bsoft.com.cn
 * @类说明 多重数据的适配器
 */

public class MultiItemQuickAdapter extends BaseMultiItemQuickAdapter<MultiDataItem, BaseViewHolder> {

    public static final int NONE_DISPLAY_PICTURE = 0;
    public static final int DISPLAY_PICTURE = 1;

    public MultiItemQuickAdapter(List<MultiDataItem> data) {
        super(data);
        addItemType(NONE_DISPLAY_PICTURE, R.layout.adapter_item_multi_none_picture);
        addItemType(DISPLAY_PICTURE, R.layout.adapter_item_multi_picture);
    }

    @Override
    protected void convert(BaseViewHolder holder, MultiDataItem item) {
        switch (holder.getItemViewType()) {
            case NONE_DISPLAY_PICTURE:
                holder.setText(R.id.tv_header, item.getTag());
                break;
            case DISPLAY_PICTURE:
                RecyclerView recyclerView = holder.getView(R.id.rv_picture);

                recyclerView.addItemDecoration(new SpacesItemDecoration(DensityUtils.dp2px(1)));
                MultiItemQuickChildAdapter adapter = new MultiItemQuickChildAdapter();
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new FullyGridLayoutManager(Utils.getContext(), 3));

                adapter.setNewData(item.getImages());

                holder.setText(R.id.tv_header, item.getTag());
                break;
        }
    }
}
