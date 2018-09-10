package com.personal.noncommercial.significantproject.moudle.bean;

/**
 * @author :lizhengcao
 * @date :2018/4/2
 * E-mail:lizc@bsoft.com.cn
 * @类说明
 */

public class HomeUIBean {
    //图片
    private int image;
    //底部标题
    private String bottomText;

    public HomeUIBean(int image, String bottomText) {
        this.image = image;
        this.bottomText = bottomText;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getBottomText() {
        return bottomText;
    }

    public void setBottomText(String bottomText) {
        this.bottomText = bottomText;
    }
}
