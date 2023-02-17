package com.wx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;
import com.wx.constant.Constant;
import com.wx.pojo.Dept;
import com.wx.resp.PageResp;
import com.wx.resp.Result;
import com.wx.service.DeptService;
import com.wx.mapper.DeptMapper;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author 86134
 * @description 针对表【t_dept】的数据库操作Service实现
 * @createDate 2023-02-08 20:08:13
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept>
        implements DeptService {
    @Resource
    private DeptMapper mapper;

    @Override
    public PageResp<List<Dept>> query(int page, int limit, String deptNo, String deptName) {
        Page<Dept> deptPage = new Page<>(page, limit);
        //查询数据
        LambdaQueryWrapper<Dept> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!StringUtils.isNullOrEmpty(deptNo), Dept::getDeptNo, deptNo)
                .like(!StringUtils.isNullOrEmpty(deptName), Dept::getDeptName, deptName);
        page(deptPage, wrapper);
        //封装数据
        PageResp<List<Dept>> listPage = new PageResp<>();
        listPage.setCount((int) deptPage.getTotal());
        listPage.setData(deptPage.getRecords());
        //返回数据
        return listPage;
    }

    @Override
    public Result insertOrUpdate(Dept dept) {
        boolean outcome = true;
        //更新
        if (dept.getId() != null) {
            dept.setEditTime(new Date());
            dept.setEditId(Integer.parseInt(MDC.get(Constant.USER_ID)));
            outcome = updateById(dept);
        } else {
            outcome = save(dept);
        }
        return outcome ? Result.success() : Result.error();
    }

    @Override
    public Result del(long ids) {
        //删除
        boolean outcome = removeById(ids);
        return outcome ? Result.success() : Result.error();
    }

    @Override
    public PageResp<List<Dept>> queryDeptListNoParam() {
        //查询数据
        List<Dept> list = list();
        //封装数据
        PageResp<List<Dept>> listPage = new PageResp<>();
        listPage.setCount(list.size());
        listPage.setData(list);
        //返回数据
        return listPage;
    }
}




