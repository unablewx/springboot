package com.wx.mapper;

import com.wx.pojo.RoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 86134
 * @description 针对表【role_menu】的数据库操作Mapper
 * @createDate 2023-02-10 15:16:43
 * @Entity com.wx.pojo.RoleMenu
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    int insertBat(@Param("roleId") Long roleId, @Param("menuIds") List<Long> menuIds);
}




