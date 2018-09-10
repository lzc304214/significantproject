package com.personal.noncommercial.significantproject.moudle.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.personal.noncommercial.significantproject.adapter.ExpandableAdapter;

import java.util.List;

/**
 * @author :lizhengcao
 * @date :2018/5/18
 * E-mail:lizc@bsoft.com.cn
 * @类说明
 */

public class DataEntity {

    private String address;
    /**
     * include : 南京区
     */

    private List<ItemDataEntity> itemData;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ItemDataEntity> getItemData() {
        return itemData;
    }

    public void setItemData(List<ItemDataEntity> itemData) {
        this.itemData = itemData;
    }

}
