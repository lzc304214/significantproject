package com.personal.noncommercial.significantproject.utils.util;

import com.alibaba.fastjson.JSON;

import java.util.List;

public class JsonUtils {

    public static <T> T parseObject(String data, Class<T> clazz) {
        return JSON.parseObject(data, clazz);
    }

    public static <T> List<T> parseArray(String data, Class<T> clazz) {
        return JSON.parseArray(data, clazz);
    }

    public static String toJsonString(Object object) {
        return JSON.toJSONString(object);
    }
}
