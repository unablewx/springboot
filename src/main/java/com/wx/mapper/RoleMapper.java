package com.wx.mapper;

import com.wx.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 86134
* @description 针对表【t_role】的数据库操作Mapper
* @createDate 2023-02-09 13:15:27
* @Entity com.wx.pojo.Role
*/
public interface RoleMapper extends BaseMapper<Role> {
    List<Role> selectListParams(@Param("roleNo") String roleNo,@Param("roleName") String roleName);
}




