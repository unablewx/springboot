package com.wx.mapper;

import com.wx.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 86134
 * @description 针对表【t_user】的数据库操作Mapper
 * @createDate 2023-02-01 13:14:47
 * @Entity com.wx.pojo.User
 */
public interface UserMapper extends BaseMapper<User> {
    List<User> selectAllMutual(@Param("userName") String userName, @Param("userMobile") String userMobile);
}




