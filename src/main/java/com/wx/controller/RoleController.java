package com.wx.controller;

import com.wx.constant.Constant;
import com.wx.pojo.Menu;
import com.wx.pojo.Role;
import com.wx.resp.PageResp;
import com.wx.resp.Result;
import com.wx.resp.TreeResp;
import com.wx.service.RoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author:Mr.Wang
 * @Date: 2023/2/9  13:17
 * @Version 1.0
 * 角色控制器
 */
@RestController
@RequestMapping({Constant.URL_ROLE, Constant.URL})
public class RoleController {
    @Autowired
    private RoleService roleService;

    @ApiOperation("查询角色列表")
    @PostMapping("list")
    public PageResp<List<Role>> queryRoleList(@RequestParam("page") int page,
                                              @RequestParam("limit") int limit,
                                              @RequestParam("varLable") String roleNo,
                                              @RequestParam("varName") String roleName) {
        return roleService.queryRoleList(page, limit, roleNo, roleName);
    }

    @ApiOperation("添加或者更新")
    @PostMapping("save")
    public Result queryDeptList(Role role) {
        return roleService.insertOrUpdate(role);
    }

    @ApiOperation("删除")
    @DeleteMapping("delete")
    public Result del(long ids) {
        return roleService.del(ids);
    }

    @ApiOperation("显示角色菜单权限")
    @GetMapping("RoleRight/tree/{id}")
    public TreeResp<List<Menu>> roleRight(@PathVariable("id") long id) {
        return roleService.roleMenus(id);
    }

    @ApiOperation("修改角色菜单权限")
    @PostMapping("RoleRight/save")
    public Result saveRoleRight(@RequestParam("roleId") long roleId,
                                @RequestParam("moduleIds") List<Long> menuIds) {
        return roleService.saveRoleMenu(roleId, menuIds);
    }

    @ApiOperation("无参数获取角色列表")
    @PostMapping("list/no")
    public PageResp<List<Role>> queryRoleListNoParam() {
        return roleService.queryRoleListNoParam();
    }
}
