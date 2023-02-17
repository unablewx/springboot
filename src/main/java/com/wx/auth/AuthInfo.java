package com.wx.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author:Mr.Wang
 * @Date: 2023/2/1  16:22
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AuthInfo {
    private long userId;
    private String username;
    private long roleId;
    private long expired;
//    private String key;//uuid
}
