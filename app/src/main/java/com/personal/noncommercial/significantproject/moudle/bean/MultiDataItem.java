package com.personal.noncommercial.significantproject.moudle.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.personal.noncommercial.significantproject.adapter.MultiItemQuickAdapter;

import java.util.List;

/**
 * @author :lizhengcao
 * @date :2018/5/21
 * E-mail:lizc@bsoft.com.cn
 * @类说明
 */

public class MultiDataItem implements MultiItemEntity {
    private String tag;
    private List<String> images;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public int getItemType() {
        return images == null || images.size() == 0 ? MultiItemQuickAdapter.NONE_DISPLAY_PICTURE : MultiItemQuickAdapter.DISPLAY_PICTURE;
    }
}
