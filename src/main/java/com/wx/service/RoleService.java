package com.wx.service;

import com.wx.pojo.Menu;
import com.wx.pojo.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.resp.PageResp;
import com.wx.resp.Result;
import com.wx.resp.TreeResp;

import java.util.List;

/**
 * @author 86134
 * @description 针对表【t_role】的数据库操作Service
 * @createDate 2023-02-09 13:15:27
 */
public interface RoleService extends IService<Role> {

    /**
     * 查询角色列表
     *
     * @param page     当前页面的页码
     * @param limit    当前页面的显示条数
     * @param roleNo   查询的角色编号
     * @param roleName 查询的角色姓名
     * @return
     */
    PageResp<List<Role>> queryRoleList(int page, int limit, String roleNo, String roleName);

    /**
     * 添加或者更新角色，有id就更新，没有id就是添加
     *
     * @param role 封装的角色数据
     * @return
     */
    Result insertOrUpdate(Role role);

    /**
     * 删除角色
     *
     * @param ids 角色对应的主键
     * @return
     */
    Result del(long ids);

    /**
     * 返回角色菜单权限
     *
     * @param id 角色id主键
     * @return
     */
    TreeResp<List<Menu>> roleMenus(long id);

    /**
     * 编辑角色菜单权限
     *
     * @param roleId  角色id主键
     * @param menuIds 菜单id主键
     * @return
     */
    Result saveRoleMenu(long roleId, List<Long> menuIds);

    /**
     * 无参方法返回角色列表
     *
     * @return
     */
    PageResp<List<Role>> queryRoleListNoParam();
}
