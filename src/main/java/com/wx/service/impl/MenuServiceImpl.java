package com.wx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.constant.Constant;
import com.wx.mapper.RoleMenuMapper;
import com.wx.pojo.Menu;
import com.wx.pojo.MenuVo;
import com.wx.pojo.RoleMenu;
import com.wx.resp.PageResp;
import com.wx.resp.Result;
import com.wx.service.MenuService;
import com.wx.mapper.MenuMapper;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 86134
 * @description 针对表【t_menu】的数据库操作Service实现
 * @createDate 2023-02-09 16:08:15
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
        implements MenuService {
    @Resource
    private MenuMapper mapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Override
    public PageResp<List<Menu>> initMenus() {
        PageResp<List<Menu>> listPageResp = new PageResp<>();
        List<Long> ids = new ArrayList<>();
        //获取角色id
        Long roleId = Long.valueOf(MDC.get(Constant.USER_ROLE));
        //查询角色菜单数据
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId, roleId);
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(wrapper);
        //根据角色菜单数据查询菜单数据
        roleMenus.forEach(roleMenu -> ids.add(roleMenu.getMenuId()));
        List<Menu> menus = mapper.selectBatchIds(ids);
        //处理菜单数据
        menus = filter(menus);
        //封装数据
        listPageResp.setCount(menus.size());
        listPageResp.setData(menus);
        //返回数据
        return listPageResp;
    }

    @Override
    public PageResp<List<Menu>> queryMenus() {
        //查询数据
        List<Menu> list = list();
        //将子菜单列表封装到父菜单列表对象数据中
        List<Menu> returnValue = filter(list);
        //封装数据
        PageResp<List<Menu>> listPageResp = new PageResp<>();
        listPageResp.setCount(returnValue.size());
        listPageResp.setData(returnValue);
        //返回数据
        return listPageResp;
    }

    //返回排序后的主菜单
    public List<Menu> filter(List<Menu> list) {
        List<Menu> sub = new ArrayList<>();
        HashMap<Long, Menu> parentMap = new HashMap<>();
        //1.找出子菜单数据
        for (Menu menu : list) {
            if (menu.getPid() != null) {
                sub.add(menu);
            } else {
                parentMap.put(menu.getId(), menu);
            }
        }
        //将子菜单进行排序
        sub = sub.stream().sorted(Comparator.comparing(Menu::getOrderValue)).collect(Collectors.toList());
        //2.将子菜单放入主菜单的属性中
        sub.forEach(menu -> {
            Long pid = menu.getPid();
            Menu parent = parentMap.get(pid);
            parent.getMenus().add(menu);
            parentMap.put(pid, parent);
        });
        //将主菜单排序

        return parentMap.values().stream()
                .sorted(Comparator.comparing(Menu::getOrderValue))
                .collect(Collectors.toList());
    }


    @Override
    public PageResp<List<MenuVo>> nodes() {
        //查询数据
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.isNull(Menu::getUrl);
        List<Menu> menuList = list(wrapper);
        List<MenuVo> menuVos = new ArrayList<>();
        menuList.forEach(menu -> {
            MenuVo menuVo = new MenuVo(menu);
            menuVos.add(menuVo);
        });
        //封装数据
        PageResp<List<MenuVo>> menuPageResp = new PageResp<>();
        menuPageResp.setCount(menuVos.size());
        menuPageResp.setData(menuVos);
        //返回数据
        return menuPageResp;
    }

    @Override
    public Result insertOrUpdate(Menu menu) {
        boolean outcome = true;
        outcome = saveOrUpdate(menu);
        return outcome ? Result.success() : Result.error();
    }

    @Override
    @Transactional
    public Result del(List<Long> ids) {
        //批量删除
        LambdaUpdateWrapper<Menu> wrapper = new LambdaUpdateWrapper<>();
        wrapper.in(Menu::getId, ids).or().in(Menu::getPid, ids);
        boolean outcome = remove(wrapper);
        //返回数据
        return outcome ? Result.success() : Result.error();
    }
}




