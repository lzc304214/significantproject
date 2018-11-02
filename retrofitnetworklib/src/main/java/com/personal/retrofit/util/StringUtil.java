package com.personal.retrofit.util;

/**
 * @author :lizhengcao
 * @date :2018/11/2
 * E-mail:lizc@bsoft.com.cn
 * @类说明
 */

public class StringUtil {
    /**
     * 非空判断
     *
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        if (null == s)
            return true;
        if (s.length() == 0)
            return true;
        if (s.trim().length() == 0)
            return true;
        return false;
    }
}
