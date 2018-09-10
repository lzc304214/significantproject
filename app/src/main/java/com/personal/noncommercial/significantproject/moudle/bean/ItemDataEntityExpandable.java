package com.personal.noncommercial.significantproject.moudle.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.personal.noncommercial.significantproject.adapter.ExpandableAdapter;

/**
 * @author :lizhengcao
 * @date :2018/5/18
 * E-mail:lizc@bsoft.com.cn
 * @类说明 可展开列表的第二层
 */

public class ItemDataEntityExpandable implements MultiItemEntity {

    private String include;

    public ItemDataEntityExpandable(String include) {
        this.include = include;
    }

    public String getInclude() {
        return include;
    }

    public void setInclude(String include) {
        this.include = include;
    }

    @Override
    public int getItemType() {
        return ExpandableAdapter.TYPE_CHILD;
    }
}
