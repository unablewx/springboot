package com.wx.mapper;

import com.wx.pojo.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 86134
 * @description 针对表【t_menu】的数据库操作Mapper
 * @createDate 2023-02-09 16:08:15
 * @Entity com.wx.pojo.Menu
 */
public interface MenuMapper extends BaseMapper<Menu> {
    int deleteByIds(@Param("ids") List<Long> ids);
}




