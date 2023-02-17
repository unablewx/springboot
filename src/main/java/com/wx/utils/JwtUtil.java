package com.wx.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.base.Preconditions;
import com.wx.auth.AuthInfo;
import com.wx.constant.Constant;
import com.wx.exception.CrmAuthException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:Mr.Wang
 * @Date: 2023/2/1  14:50
 * @Version 1.0
 */
public class JwtUtil {
    private JwtUtil() {
    }

    /**
     * 生成Token
     *
     * @param authInfo   需要序列化的对象
     * @param expireDate 过期时间
     * @param secret     加密密码
     * @return
     */
    public static String getToken(AuthInfo authInfo, Date expireDate, String secret) {
        //判断条件是否成立
        Preconditions.checkArgument(authInfo != null, "加密内容不能为 null");
        Preconditions.checkArgument(expireDate != null, "过期时间异常");
        Preconditions.checkArgument(secret != null, "加密密码不能为空");
        //填充数据
        Map<String, Object> params = new HashMap<>();
        params.put("alg", "HS256");
        params.put("typ", "JWT");
        //使用jwt生成token
        return JWT.create()
                .withHeader(params)//头部
                //对象的各个参数
                .withClaim(Constant.USER_ID, authInfo.getUserId())
                .withClaim(Constant.USER_NAME, authInfo.getUsername())
                .withClaim(Constant.USER_ROLE, authInfo.getRoleId())
                .withIssuedAt(new Date())//签名时间
                .withExpiresAt(expireDate)//过期时间
                .sign(Algorithm.HMAC256(secret));//签名
    }

    /**
     * 验证token的正确性，并返回相应的authInfo对象
     *
     * @param token
     * @param secret
     * @return
     */
    public static AuthInfo verifyToken(String token, String secret) throws CrmAuthException {
        JWTVerifier jwtVerifier = null;
        DecodedJWT jwt = null;
        //创建验证器
        jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
        //验证token
        try {
            jwt = jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new CrmAuthException("凭证已过期，请重新登录");
        }
        //返回AuthInfo对象
        return new AuthInfo().
                setUserId(jwt.getClaim(Constant.USER_ID).asInt()).
                setUsername(jwt.getClaim(Constant.USER_NAME).asString()).
                setRoleId(jwt.getClaim(Constant.USER_ROLE).asLong());
    }

}
