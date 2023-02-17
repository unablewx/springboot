package com.wx.auth;

import com.wx.exception.CrmAuthException;

/**
 * @Author:Mr.Wang
 * @Date: 2023/2/1  19:31
 * @Version 1.0
 */
public interface Authenticator {
    /**
     * 将前端传过来的token信息转换成AuthInfo
     * @param token
     * @return
     */
    AuthInfo auth(String token) throws CrmAuthException;

}
