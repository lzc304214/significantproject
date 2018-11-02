package com.personal.retrofit.model;

/**
 * @author :lizhengcao
 * @date :2018/11/2
 * E-mail:lizc@bsoft.com.cn
 * @类说明
 */

public class ResultModel<T> extends BaseModel {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
