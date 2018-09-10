package com.personal.noncommercial.significantproject.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.moudle.bean.DataEntityExpandable;
import com.personal.noncommercial.significantproject.moudle.bean.ItemDataEntityExpandable;

import java.util.List;

/**
 * @author :lizhengcao
 * @date :2018/5/18
 * E-mail:lizc@bsoft.com.cn
 * @类说明 可展开的列表适配器
 */

public class ExpandableAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_PARENT = 0;
    public static final int TYPE_CHILD = 1;


    public ExpandableAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_PARENT, R.layout.adapter_item_parent);
        addItemType(TYPE_CHILD, R.layout.adapter_item_child);

    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, MultiItemEntity multiItemEntity) {
        switch (baseViewHolder.getItemViewType()) {
            case TYPE_PARENT:
                DataEntityExpandable itemParent = (DataEntityExpandable) multiItemEntity;
                baseViewHolder.setText(R.id.tv_parent, itemParent.getAddress());

                baseViewHolder.setImageResource(R.id.iv_arrow,
                        itemParent.isExpanded() ? R.mipmap.arrow_more : R.mipmap.arrow_down);
                baseViewHolder.itemView.setOnClickListener(v -> {
                    int pos = baseViewHolder.getAdapterPosition();
                    if (itemParent.isExpanded()) {
                        collapse(pos);
                    } else {
                        expand(pos);
                    }

                });
                break;
            case TYPE_CHILD:
                ItemDataEntityExpandable itemChild = (ItemDataEntityExpandable) multiItemEntity;
                baseViewHolder.setText(R.id.tv_child, itemChild.getInclude());
                break;
        }
    }
}
