package com.personal.noncommercial.significantproject.moudle.bean;

import java.io.Serializable;


/**
 * @author :lizhengcao
 * @date :2018/3/28
 * E-mail:lizc@bsoft.com.cn
 * @类说明
 */
public class LoginData implements Serializable {
    private String describle;
    private String address;
    private String tel;

    public String getDescrible() {
        return describle;
    }

    public void setDescrible(String describle) {
        this.describle = describle;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

}
