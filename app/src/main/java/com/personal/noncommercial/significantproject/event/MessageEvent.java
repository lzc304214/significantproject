package com.personal.noncommercial.significantproject.event;

/**
 * @author :lizhengcao
 * @date :2018/4/19
 * E-mail:lizc@bsoft.com.cn
 * @类说明
 */

public class MessageEvent {
    private String msg;

    public MessageEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
