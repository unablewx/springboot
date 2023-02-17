package com.wx.service;

import com.wx.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.resp.LoginResp;
import com.wx.resp.PageResp;
import com.wx.resp.Result;

import java.util.List;

/**
 * @author 86134
 * @description 针对表【t_user】的数据库操作Service
 * @createDate 2023-02-01 13:14:47
 */
public interface UserService extends IService<User> {
    /**
     * 登录服务，从数据库查询登录，登陆成功后生成鉴权信息并返回前端
     *
     * @param account
     * @param password
     * @return
     */
    LoginResp login(String account, String password);

    /**
     * 查询数据
     *
     * @param page 当前页面的所在的页数
     * @param limit 当前页面显示数据的条数
     * @param userName 搜索的用户名(模糊查询)
     * @param userMobile 搜索的电话号码
     * @return
     */
    PageResp<List<User>> queryUserList(int page, int limit, String userName, String userMobile);

    /**
     * 更新数据：如果存在User主键是更新，否则就是新增。
     * @param user 前端传入的user用户
     * @return
     */
    Result insertOrUpdate(User user);

    /**
     * 删除数据
     * @param id 前端用户的主键
     * @return
     */
    Result delete(long id);

    /**
     * 重置密码
     * @param id 前端用户的主键
     * @return
     */
    Result resetPassword(long id);
}
