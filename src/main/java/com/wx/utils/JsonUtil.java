package com.wx.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Author:Mr.Wang
 * @Date: 2023/2/1  14:31
 * @Version 1.0
 */
public class JsonUtil {

    private JsonUtil() {
    }

    public static ObjectMapper mapper = new ObjectMapper();


    /**
     * 将对象转换成字符串
     *
     * @param obj 需要转成json的对象
     * @return 对象的json字符串
     */
    public static String objectToJson(Object obj) {
        String json = null;
        try {
            json = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     * 将字符串转换成对象
     *
     * @param jsons   需要转换的json字符串
     * @param tClass 需要返回的类字节码
     * @param <T>    指定的类
     * @return 指定的类生成的对象
     */
    public static <T> T jsonToObject(String jsons, Class<T> tClass) {
        T t = null;
        try {
            t = mapper.readValue(jsons, tClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return t;
    }
}
