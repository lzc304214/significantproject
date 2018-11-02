package com.personal.retrofit.model;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * @author :lizhengcao
 * @date :2018/11/2
 * E-mail:lizc@bsoft.com.cn
 * @类说明 基本实体类
 */

public class BaseModel implements Serializable {
    private int code;
    private String msg;
    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

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
}
