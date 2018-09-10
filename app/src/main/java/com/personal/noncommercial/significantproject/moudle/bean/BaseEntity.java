package com.personal.noncommercial.significantproject.moudle.bean;

import java.io.Serializable;

/**
 * @author :lizhengcao
 * @date :2018/3/28
 * E-mail:lizc@bsoft.com.cn
 * @类说明 实体类的基本类型
 */

public class BaseEntity<Data> implements Serializable {
    private int code;
    private String msg;
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
