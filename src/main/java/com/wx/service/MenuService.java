package com.wx.service;

import com.wx.pojo.Dept;
import com.wx.pojo.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.pojo.MenuVo;
import com.wx.resp.PageResp;
import com.wx.resp.Result;

import java.util.List;

/**
 * @author 86134
 * @description 针对表【t_menu】的数据库操作Service
 * @createDate 2023-02-09 16:08:15
 */
public interface MenuService extends IService<Menu> {

    /**
     * 初始化菜单列表
     *
     * @return
     */
    PageResp<List<Menu>> initMenus();

    /**
     * 查询菜单列表
     *
     * @return
     */
    PageResp<List<Menu>> queryMenus();

    /**
     * 添加或者更新菜单，有id就更新，没有id就是添加
     *
     * @param menu 封装的菜单数据
     * @return
     */
    Result insertOrUpdate(Menu menu);

    /**
     * 删除部门
     *
     * @param ids 部门对应的主键
     * @return
     */
    Result del(List<Long> ids);

    /**
     * 查找父亲节点
     *
     * @return
     */
    PageResp<List<MenuVo>> nodes();
//    PageResp<List<Menu>> nodes();
}
