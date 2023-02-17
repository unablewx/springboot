package com.wx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wx.constant.Constant;
import com.wx.mapper.RoleMenuMapper;
import com.wx.pojo.Menu;
import com.wx.pojo.Role;
import com.wx.pojo.RoleMenu;
import com.wx.resp.PageResp;
import com.wx.resp.Result;
import com.wx.resp.TreeResp;
import com.wx.service.MenuService;
import com.wx.service.RoleService;
import com.wx.mapper.RoleMapper;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 86134
 * @description 针对表【t_role】的数据库操作Service实现
 * @createDate 2023-02-09 13:15:27
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
        implements RoleService {

    @Resource
    private RoleMapper mapper;
    @Autowired
    private MenuService service;
    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Override
    public PageResp<List<Role>> queryRoleList(int page, int limit, String roleNo, String roleName) {
        PageHelper.startPage(page, limit);
        //查询数据
        List<Role> roles = mapper.selectListParams(roleNo, roleName);
        PageInfo rolePage = new PageInfo(roles);
        //封装数据
        PageResp<List<Role>> listPage = new PageResp<>();
        listPage.setCount(new Long(rolePage.getTotal()).intValue());
        listPage.setData(roles);
        //返回数据
        return listPage;
    }

    @Override
    public Result insertOrUpdate(Role role) {
        boolean outcome = true;
        //更新
        if (role.getId() != null) {
            role.setEditTime(new Date());
            role.setUpdateId(Long.parseLong(MDC.get(Constant.USER_ID)));
            outcome = updateById(role);
        } else {
            role.setCreateTime(new Date());
            role.setCreateId(Long.parseLong(MDC.get(Constant.USER_ID)));
            outcome = save(role);
        }
        return outcome ? Result.success() : Result.error();
    }

    @Transactional
    @Override
    public Result del(long ids) {
        //删除角色
        boolean outcome = removeById(ids);
        //删除角色菜单权限
        LambdaUpdateWrapper<RoleMenu> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(RoleMenu::getRoleId,ids);
        roleMenuMapper.delete(wrapper);
        //返回结果
        return outcome ? Result.success() : Result.error();
    }

    @Override
    public TreeResp<List<Menu>> roleMenus(long id) {
        TreeResp<List<Menu>> treeResp = new TreeResp<>();
        //查询所有菜单数据
        PageResp<List<Menu>> pageResp = service.queryMenus();
        //查询角色拥有的菜单权限
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId, id);
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(wrapper);
        //封装数据
        treeResp.setData(pageResp.getData());
        treeResp.setList(roleMenus);
        return treeResp;
    }

    @Transactional
    @Override
    public Result saveRoleMenu(long roleId, List<Long> menuIds) {
        //先删除角色相关信息
        LambdaUpdateWrapper<RoleMenu> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(RoleMenu::getRoleId, roleId);
        roleMenuMapper.delete(wrapper);
        //重新添加角色和菜单信息
        if (menuIds != null && menuIds.size() != 0) {
            int count = roleMenuMapper.insertBat(roleId, menuIds);
            return count == menuIds.size() ? Result.success() : Result.error();
        }
        return Result.success();
    }

    @Override
    public PageResp<List<Role>> queryRoleListNoParam() {
        //查询数据
        List<Role> roleList = list();
        //封装数据
        PageResp<List<Role>> listPage = new PageResp<>();
        listPage.setCount(roleList.size());
        listPage.setData(roleList);
        //返回数据
        return listPage;
    }
}




