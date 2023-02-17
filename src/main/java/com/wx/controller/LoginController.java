package com.wx.controller;

import com.wx.constant.Constant;
import com.wx.resp.LoginResp;
import com.wx.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author:Mr.Wang
 * @Date: 2023/1/26  20:22
 * @Version 1.0
 */
@RestController
@RequestMapping(Constant.URL)
@Api(tags = "登录描述信息")
public class LoginController {
    @Autowired
    private UserService userService;

    @ApiOperation("查询账户登录")
    @PostMapping("login")
    public LoginResp login(@RequestParam("username") String account, @RequestParam("password") String password) {
        return userService.login(account, password);
    }
}
