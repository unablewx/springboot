package com.wx.controller;

import com.wx.constant.Constant;
import com.wx.pojo.Menu;
import com.wx.pojo.MenuVo;
import com.wx.resp.PageResp;
import com.wx.resp.Result;
import com.wx.service.MenuService;
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
@RequestMapping({Constant.URL, Constant.URL_MODULE})
@Api("菜单类控制器")
public class MenuController {
    @Autowired
    private MenuService service;

    @ApiOperation("显示部门列表,整个页面的左半部分")
    @GetMapping("menu")
    public PageResp<List<Menu>> queryMenuList() {
        return service.initMenus();
    }

    @ApiOperation("显示部门列表,菜单管理的数据")
    @PostMapping("list")
    public PageResp<List<Menu>> queryMenus() {
        return service.queryMenus();
    }

    @ApiOperation("获取父级菜单")
    @PostMapping("nodes")
    public PageResp<List<MenuVo>> nodes() {
        return service.nodes();
    }


    @ApiOperation("添加或者更新")
    @PostMapping("save")
    public Result queryDeptList(@RequestParam(value = "moduleId",required = false) Long menuId,
                                @RequestParam(value = "parentId",required = false) Long parentId,
                                @RequestParam("moduleIcon") String menuIcon,
                                @RequestParam("moduleOrder") int menuOrder,
                                @RequestParam("moduleUrl") String menuUrl,
                                @RequestParam("moduleName") String menuName) {
        Menu menu = new Menu(menuId, menuIcon, menuName, null, menuUrl, parentId, menuOrder, null);
        return service.insertOrUpdate(menu);
    }

    @ApiOperation("删除")
    @DeleteMapping("delete")
    public Result del(@RequestParam("ids") List<Long> ids) {
        return service.del(ids);
    }
}
