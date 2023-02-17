package com.wx.auth;

import com.wx.exception.CrmAuthException;
import com.wx.utils.JwtUtil;
import com.wx.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author:Mr.Wang
 * @Date: 2023/2/1  19:33
 * @Version 1.0
 */
@Component
public class AuthenticatorImpl implements Authenticator {
    //    @Value("${jwt.auth.secret}")
    private String secret;

    public AuthenticatorImpl(String secret) {
        this.secret = secret;
    }

    public AuthenticatorImpl() {
    }

    /**
     * 有的token形式可能是 Bearer xxxx
     *
     * @param token 需要验证的token
     * @return AuthInfo对象
     */
    @Override
    public AuthInfo auth(String token) throws CrmAuthException {
        String authToken = null;
        int index = token.indexOf(" ");
        if (index == -1) {
            authToken = token;
        } else {
            String type = token.substring(0, index);
            if (!"Bearer".equals(type)) {
                throw new CrmAuthException(String.format("无法识别的token类型[%s]", type));
            } else {
                authToken = token.substring(index).trim();
            }
        }
        return JwtUtil.verifyToken(authToken, secret);
    }

}
