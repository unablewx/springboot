package com.wx.controller;

import com.wx.constant.Constant;
import com.wx.pojo.Dept;
import com.wx.pojo.Role;
import com.wx.resp.PageResp;
import com.wx.resp.Result;
import com.wx.service.DeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author:Mr.Wang
 * @Date: 2023/2/8  19:40
 * @Version 1.0
 */
@RestController
@RequestMapping(Constant.URL_DEPT)
@Api("部门类控制器")
public class DeptController {
    @Autowired
    private DeptService service;

    @ApiOperation("显示部门列表")
    @PostMapping("list")
    public PageResp<List<Dept>> queryDeptList(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit,
            @RequestParam("deptNo") String deptNo,
            @RequestParam("deptName") String deptName) {
        return service.query(page,limit,deptNo,deptName);
    }

    @ApiOperation("添加或者更新")
    @PostMapping("save")
    public Result queryDeptList(Dept dept) {
        return service.insertOrUpdate(dept);
    }

    @ApiOperation("删除")
    @DeleteMapping("delete")
    public Result del(long ids) {
        return service.del(ids);
    }

    @ApiOperation("无参数获取角色列表")
    @PostMapping("list/no")
    public PageResp<List<Dept>> queryRoleListNoParam() {
        return service.queryDeptListNoParam();
    }
}
