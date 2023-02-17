package com.wx.controller;

import com.wx.constant.Constant;
import com.wx.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author:Mr.Wang
 * @Date: 2023/2/2  10:25
 * @Version 1.0
 */
@RestController
@RequestMapping(Constant.URL)
@Api("测试类控制器")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class TestController {
    @Autowired(required = false)
    private RedisUtils redisUtils;

    @GetMapping("/2")
    public String test() {
        System.out.println(MDC.get(Constant.USER_ID));
        return MDC.get(Constant.USER_NAME);
    }

    @ApiOperation("redis存数据")
    @GetMapping("/set")
    public String queryTest(@RequestParam("id")Long id){
        redisUtils.set(id.toString(),"redis");
        return "ok";
    }

    @ApiOperation("redis存数据")
    @GetMapping("/get")
    public String queryTest1(@RequestParam("id")Long id){
        return redisUtils.get(id.toString()).toString();
    }
}
