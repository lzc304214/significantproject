package com.personal.noncommercial.significantproject.moudle.bean;


import java.io.Serializable;

/**
 * @author :lizhengcao
 * @date :2019/4/6
 * E-mail:lizc@bsoft.com.cn
 * @类说明
 */
public class User implements Serializable {
    private String name;
    private int age;
    private boolean isMan;
    private int colorType;

    public User(String name, int age, boolean isMan, int colorType) {
        this.name = name;
        this.age = age;
        this.isMan = isMan;
        this.colorType = colorType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isMan() {
        return isMan;
    }

    public void setMan(boolean man) {
        isMan = man;
    }

    public int getColorType() {
        return colorType;
    }

    public void setColorType(int colorType) {
        this.colorType = colorType;
    }
}
