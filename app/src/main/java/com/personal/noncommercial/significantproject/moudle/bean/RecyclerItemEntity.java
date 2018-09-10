package com.personal.noncommercial.significantproject.moudle.bean;

import java.util.List;

/**
 * @author :lizhengcao
 * @date :2018/5/18
 * E-mail:lizc@bsoft.com.cn
 * @类说明
 */

public class RecyclerItemEntity {


    /**
     * code : 1
     * data : [{"address":"江苏","itemData":[{"include":"南京区"},{"include":"苏州区"},{"include":"无锡区"},{"include":"苏中区"},{"include":"太仓区"}]},{"address":"安徽","itemData":[{"include":"合肥区"},{"include":"阜阳区"},{"include":"安庆区"},{"include":"芜湖区"},{"include":"马鞍山区"}]}]
     * msg : 成功
     */

    private int code;
    private String msg;
    /**
     * address : 江苏
     * itemData : [{"include":"南京区"},{"include":"苏州区"},{"include":"无锡区"},{"include":"苏中区"},{"include":"太仓区"}]
     */

    private List<DataEntity> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

}
