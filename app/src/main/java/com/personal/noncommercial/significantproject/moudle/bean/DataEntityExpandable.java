package com.personal.noncommercial.significantproject.moudle.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.personal.noncommercial.significantproject.adapter.ExpandableAdapter;

import java.util.List;

/**
 * @author :lizhengcao
 * @date :2018/5/18
 * E-mail:lizc@bsoft.com.cn
 * @类说明 可展开第一层
 */

public class DataEntityExpandable extends AbstractExpandableItem<ItemDataEntityExpandable> implements MultiItemEntity {

    private String address;

    public DataEntityExpandable(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return ExpandableAdapter.TYPE_PARENT;
    }
}
