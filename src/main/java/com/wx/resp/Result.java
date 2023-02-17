package com.wx.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:Mr.Wang
 * @Date: 2023/1/11  9:17
 * @Version 1.0
 * 统一返回结果实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Component
public class Result {
    //操作是否成功
    private boolean success = true;
    //状态码
    private Integer states;
    //消息
    private String msg;

    //返回的操作数据
//    private Map<String, Object> data;
    public static Result success() {
        Result result = new Result();
        result.setSuccess(true)
                .setStates(20000)
                .setMsg("操作成功");
//                .setData(new HashMap<>());
        return result;
    }

    public static Result error() {
        Result result = new Result();
        result.setSuccess(false)
                .setStates(20001)
                .setMsg("操作失败");
//                .setData(new HashMap<>());
        return result;
    }
//    /**
//     * 封装数据通过传递Map
//     *
//     * @param data 传递进来的封装数据的Map
//     * @return Result
//     */
//    public Result setData(Map<String, Object> data) {
//        this.data = data;
//        return this;
//    }
//
//    /**
//     * 封装数据通过传递键值对
//     *
//     * @param key 传递进来的键
//     * @param value 传递进来的值
//     * @return Result
//     */
//    public Result setData(String key, Object value) {
//        this.data.put(key, value);
//        return this;
//    }
}
