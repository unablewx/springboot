package com.wx.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.util.StringUtils;
import com.wx.auth.AuthInfo;
import com.wx.constant.Constant;
import com.wx.mapper.RoleMapper;
import com.wx.pojo.User;
import com.wx.resp.LoginResp;
import com.wx.resp.PageResp;
import com.wx.resp.Result;
import com.wx.service.UserService;
import com.wx.mapper.UserMapper;
import com.wx.utils.JwtUtil;
import com.wx.utils.RedisUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author 86134
 * @description 针对表【t_user】的数据库操作Service实现
 * @createDate 2023-02-01 13:14:47
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;
    @Value("${jwt.auth.secret}")
    private String secret;
    @Value("${jwt.auth.expired}")
    private Integer expired;
    @Value("${jwt.auth.default-password}")
    private String defaultPassword;
    @Autowired(required = false)
    private RedisUtils redisUtils;

    @Override
    public LoginResp login(String account, String password) {
        LoginResp loginResp = new LoginResp();
        //查询数据
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAccount, account).eq(User::getPassword, password);
        User user = getOne(wrapper);
        //返回数据
        if (user == null) {
            //登陆失败
            loginResp.setSuccess(false).setMsg("账户或者密码不正确.");
        } else {
            //登陆成功
            //生成过期时间
            Date expireDate = new Date(new Date().getTime() + expired);
            //生成鉴权信息
            AuthInfo authInfo = new AuthInfo(user.getId(), user.getAccount(), user.getRoleId(), expireDate.getTime());
            String token = JwtUtil.getToken(authInfo, expireDate, secret);
            //添加到 redis 中
            if (redisUtils != null) {
                redisUtils.set(token,authInfo);
            }
            //封装数据，填充token
            user.setToken(token);
            loginResp.setData(user);
        }
        return loginResp;
    }


    @Override
    public PageResp<List<User>> queryUserList(int page, int limit, String userName, String userMobile) {
        PageHelper.startPage(page, limit);
        //查询数据
        List<User> users = userMapper.selectAllMutual(userName, userMobile);
        PageInfo<User> userPageInfo = new PageInfo<>(users);
        //封装数据
        PageResp<List<User>> pageResp = new PageResp<>();
        pageResp.setCount(users.size());
        pageResp.setData(users);
        //返回数据
        return pageResp;
    }

    @Override
    public Result insertOrUpdate(User user) {
        boolean outcome;
        //主键不为空就是更新
        if (user.getId() != null) {
            user.setEditTime(new Date());
            user.setUpdateUser(Long.parseLong(MDC.get(Constant.USER_ID)));
            outcome = updateById(user);
        } else {
            //主键为空就是新增
            user.setPassword(defaultPassword);
            user.setCreateTime(new Date());
            user.setCreateUser(Long.parseLong(MDC.get(Constant.USER_ID)));
            outcome = save(user);
        }
        //返回数据
        return outcome ? Result.success() : Result.error();
    }

    @Override
    public Result delete(long ids) {
        //删除数据
        boolean outcome = removeById(ids);
        //返回数据
        return outcome ? Result.success() : Result.error();
    }

    @Override
    public Result resetPassword(long id) {
        //user赋值
        User user = new User();
        user.setPassword(defaultPassword);
        user.setId(id);
        //重置密码
        boolean outcome = updateById(user);
        //返回数据
        return outcome ? Result.success() : Result.error();
    }
}



