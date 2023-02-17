package com.wx.config;

import com.google.common.base.Joiner;
import com.wx.auth.AuthFilter;
import com.wx.auth.AuthenticatorImpl;
import com.wx.auth.AuthenticatorRedisImpl;
import com.wx.constant.Constant;
import com.wx.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import javax.servlet.Filter;

/**
 * @Author:Mr.Wang
 * @Date: 2023/2/1  19:54
 * @Version 1.0
 */
@Configuration
public class AuthConfig {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Value("${jwt.auth.redis.enable}")
    private boolean redisEnable;
    @Autowired
    private ApplicationContext context;

    @Bean
    public FilterRegistrationBean authFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        //自己构造的过滤器
        AuthFilter authFilter = null;
        if (redisEnable) {
            authFilter = new AuthFilter(authenticator(), authenticatorRedis());
        } else {
            authFilter = new AuthFilter(authenticator());
        }
//        AuthFilter authFilter = new AuthFilter(authenticator());
        //将自己构造的过滤器放到 FilterRegistrationBean中
        filterRegistrationBean.setFilter(authFilter);
        //添加过滤路径
        filterRegistrationBean.addUrlPatterns(Constant.URL + "*");
        //添加不需要过滤的路径,xxx表示后续需要的话可以继续添加
        filterRegistrationBean.addInitParameter(Constant.UN_FILTER_KEY, Joiner.on(",")
                .join(Constant.URL + "login", Constant.URL + "xxx"));
        //启动
        filterRegistrationBean.setEnabled(true);
        return filterRegistrationBean;
    }

    @Bean
    public AuthenticatorImpl authenticator() {
        String secret = context.getEnvironment().getProperty("jwt.auth.secret");
        return new AuthenticatorImpl(secret);
    }


    @Bean
    @ConditionalOnProperty(value = "jwt.auth.redis.enable", havingValue = "true")
    public RedisUtils redisUtils() {
        return new RedisUtils(redisTemplate);
    }

    @Bean
    @ConditionalOnProperty(value = "jwt.auth.redis.enable", havingValue = "true")
    public AuthenticatorRedisImpl authenticatorRedis() {
        return new AuthenticatorRedisImpl(redisUtils());
    }


}
