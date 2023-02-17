package com.wx.service;

import com.wx.pojo.Dept;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.resp.PageResp;
import com.wx.resp.Result;

import java.util.List;

/**
 * @author 86134
 * @description 针对表【t_dept】的数据库操作Service
 * @createDate 2023-02-08 20:08:13
 */
public interface DeptService extends IService<Dept> {

    /**
     * 查询部门列表
     *
     * @param page     当前页面的页码
     * @param limit    当前页面的显示条数
     * @param deptNo   查询的部门编号
     * @param deptName 查询的部门姓名
     * @return
     */
    PageResp<List<Dept>> query(int page, int limit, String deptNo, String deptName);

    /**
     * 添加或者更新部门，有id就更新，没有id就是添加
     *
     * @param dept 封装的部门数据
     * @return
     */
    Result insertOrUpdate(Dept dept);

    /**
     * 删除部门
     * @param ids 部门对应的主键
     * @return
     */
    Result del(long ids);

    /**
     * 无参获取部门列表
     * @return
     */
    PageResp<List<Dept>> queryDeptListNoParam();
}
