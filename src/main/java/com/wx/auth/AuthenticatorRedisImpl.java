package com.wx.auth;

import com.wx.exception.CrmAuthException;
import com.wx.utils.RedisUtils;

/**
 * @Author:Mr.Wang
 * @Date: 2023/2/12  10:29
 * @Version 1.0
 */
public class AuthenticatorRedisImpl implements Authenticator{
    private RedisUtils redisUtils;

    public AuthenticatorRedisImpl(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }


    public AuthenticatorRedisImpl() {
    }

    /**
     * 有的token形式可能是 Bearer xxxx
     *
     * @param token 需要验证的token
     * @return AuthInfo对象
     */
    @Override
    public AuthInfo auth(String token) throws CrmAuthException {
        if (redisUtils == null) {
            return null;
        }
        Object o = redisUtils.get(token);
        AuthInfo authInfo = null;
        if (o == null) {
            redisUtils.remove(token);
            throw new CrmAuthException("redis 未找到 token");
        } else {
            authInfo = (AuthInfo) o;
            if (authInfo.getExpired() < System.currentTimeMillis()) {
                redisUtils.remove(token);
                throw new CrmAuthException("token信息已过期");
            }
        }
        return authInfo;
    }
}
