package com.personal.noncommercial.significantproject.moudle.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author :lizhengcao
 * @date :2018/5/31
 * E-mail:lizc@bsoft.com.cn
 * @类说明 Build > Make projec 触发greenDao生成Dao类
 * 默认在build / generated / source / greendao中生成Java源代码
 */

@Entity
public class UserBean {

    @Id(autoincrement = true)
    private Long id;
    private int age;
    private String name;
    private String address;


    @Generated(hash = 1870753867)
    public UserBean(Long id, int age, String name, String address) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.address = address;
    }

    @Generated(hash = 1203313951)
    public UserBean() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
