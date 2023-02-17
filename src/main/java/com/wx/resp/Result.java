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

    public static Result success() {
        Result result = new Result();
        result.setSuccess(true)
                .setStates(20000)
                .setMsg("操作成功");
        return result;
    }

    public static Result error() {
        Result result = new Result();
        result.setSuccess(false)
                .setStates(20001)
                .setMsg("操作失败");
        return result;
    }
}
