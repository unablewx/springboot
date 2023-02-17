package com.wx.resp;

import com.wx.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author:Mr.Wang
 * @Date: 2023/1/26  20:30
 * @Version 1.0
 * 用于返回登陆界面的前端信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class LoginResp extends Result {
    private User data;
}
