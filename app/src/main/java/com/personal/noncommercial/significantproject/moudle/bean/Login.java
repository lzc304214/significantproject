package com.personal.noncommercial.significantproject.moudle.bean;

import java.io.Serializable;

import fit.SharedPreferenceAble;

/**
 * @author :lizhengcao
 * @date :2018/3/28
 * E-mail:lizc@bsoft.com.cn
 * @类说明 登录实体类
 */

@SharedPreferenceAble
public class Login implements Serializable {
    /**
     * msg : 成功
     * code : 1
     * data : {"describle":"奋斗的IT程序员","address":"南京","tel":"13339053971"}
     */

    private String msg;
    private int code;
    /**
     * describle : 奋斗的IT程序员
     * address : 南京
     * tel : 13339053971
     */

    private LoginData data;

    public LoginData getData() {
        return data;
    }

    public void setData(LoginData data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
