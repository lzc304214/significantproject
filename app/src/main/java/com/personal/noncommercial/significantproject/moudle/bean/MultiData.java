package com.personal.noncommercial.significantproject.moudle.bean;

import java.util.List;

/**
 * @author :lizhengcao
 * @date :2018/5/21
 * E-mail:lizc@bsoft.com.cn
 * @类说明
 */

public class MultiData {


    /**
     * code : 1
     * data : [{"images":["http://bzpic.spriteapp.cn/250x445/picture2/M00/0A/34/wKiFWVOnjOKAG256AAF-TNkxjfk670.jpg","http://bzpic.spriteapp.cn/250x445/picture1/M00/0E/11/wKiFR1ONNYGAN3nsAAHdvCJzyXQ051.jpg","http://bzpic.spriteapp.cn/250x445/picture1/M00/0E/54/wKiFQ1OepAqAP_cdAAC2Q6JG6Qw575.jpg","http://bzpic.spriteapp.cn/250x445/picture1/M00/0E/81/wKiFR1OqdWKAKMkOAACZl-d08cM087.jpg"],"tag":"MYOTEE 萌脸小撄"},{"images":["http://bzpic.spriteapp.cn/250x445/picture2/M00/0A/7C/wKiFRlO6N0eAFzAJAAJYpqYCzIE766.jpg"],"tag":"韩国插画小人"},{"tag":"小荧光"}]
     * msg : 成功
     */

    private int code;
    private String msg;
    /**
     * images : ["http://bzpic.spriteapp.cn/250x445/picture2/M00/0A/34/wKiFWVOnjOKAG256AAF-TNkxjfk670.jpg","http://bzpic.spriteapp.cn/250x445/picture1/M00/0E/11/wKiFR1ONNYGAN3nsAAHdvCJzyXQ051.jpg","http://bzpic.spriteapp.cn/250x445/picture1/M00/0E/54/wKiFQ1OepAqAP_cdAAC2Q6JG6Qw575.jpg","http://bzpic.spriteapp.cn/250x445/picture1/M00/0E/81/wKiFR1OqdWKAKMkOAACZl-d08cM087.jpg"]
     * tag : MYOTEE 萌脸小撄
     */

    private List<MultiDataItem> data;

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

    public List<MultiDataItem> getData() {
        return data;
    }

    public void setData(List<MultiDataItem> data) {
        this.data = data;
    }
}
