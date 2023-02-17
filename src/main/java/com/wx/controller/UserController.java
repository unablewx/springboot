package com.wx.controller;

import com.wx.constant.Constant;
import com.wx.pojo.User;
import com.wx.resp.PageResp;
import com.wx.resp.Result;
import com.wx.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author:Mr.Wang
 * @Date: 2023/2/2  15:53
 * @Version 1.0
 */
@RestController
@RequestMapping(Constant.URL_USER)
@Api(tags = "用户管理信息")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation("查询用户列表")
    @PostMapping("list")
    public PageResp<List<User>> queryUserList(@RequestParam("page") Integer page,
                                        @RequestParam("limit") Integer limit,
                                        @RequestParam(value = "userName", required = false) String userName,
                                        @RequestParam(value = "userMobile", required = false) String userMobile) {
        return userService.queryUserList(page, limit, userName, userMobile);
    }

    @ApiOperation("添加用户")
    @PostMapping("save")
    public Result insertOrUpdate(User user) {
        return userService.insertOrUpdate(user);
    }

    @ApiOperation("删除用户")
    @DeleteMapping("delete")
    public Result delete(@RequestParam("ids") long ids) {
        return userService.delete(ids);
    }

    @ApiOperation("重置密码")
    @PostMapping("pwd")
    public Result reset(@RequestParam("userId") long id) {
        return userService.resetPassword(id);
    }
}
