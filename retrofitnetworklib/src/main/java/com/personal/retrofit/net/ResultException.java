package com.personal.retrofit.net;

/**
 * Created by wangcy.fnst on 2016/6/16.
 */
public class ResultException extends RuntimeException {

    private int errCode = 0;

    public ResultException(int errCode, String msg) {
        super(msg);
        this.errCode = errCode;
    }

    public int getErrCode() {
        return errCode;
    }
}